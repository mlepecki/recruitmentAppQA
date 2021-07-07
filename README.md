# Business Background

Below APP is using by clients who have an auto fleet. 
Clients use app for calculate discount for their cars tenants. 
Once per month, client are sending raw data (plain list of transactions from all the tenants). 
APP consume the transactions in two steps: 

- checking and add discount 
- creating a summary (PER tenant) and send back to the client as simple JSON format.

## 1. Discounts
APP calculate the discount in two steps:
- calculate discount based on transactionType
- calculate discount based on transactionDate

After first step, the amounts of transactions will have reduced by calculated discount value and pass to calculate discount based on transaction Date. 

- #### Based on transaction Type
  This discount will be added to the transaction amount based on specific transactionType. 
In that case, value of discount can be change and store in transaction.properties file. 
Please see below table:

| Transaction type | Discount value |
|:----------------:|:--------------:|
| BANK_TRANSFER    |       5%       |
| MOBILE_TRANSFER  |       2%       |
| CASH             |       1%       |

- #### Based on transaction Date
  After the first step, transactions with the new values of transaction amounts (if discount was add in previous step) are passed to the next discount processor - based on transactionDate.
  This discount will be added to the transaction amount when the effectiveDate of transaction has occurred in last period.
  > Note:  Period is defining by time unit so can be: DAY, WEEK, MONTH).

    - for DAILY transaction periodicity:
        - APP will charge the discount when the effectiveDate of transaction occurred in last day from now.
    - for WEEKLY transaction periodicity:
        - APP will charge the discount when the effectiveDate of transaction occurred in last week from now.
    - for MONTHLY transaction periodicity:
        - APP will charge the discount when the effectiveDate of transaction occurred in last month from now.

  >This discount cannot be changed. It is always 5% of transaction amount.

- ### EXAMPLE
    Given Transaction:
    - amount: 10,
    - transactionType: BANK_TRANSFER
    - periodicity: DAILY
    - transactionDate: 2021-07-05T14:00 
      
    Processing Date: 2021-07-06T12:00

1. Calculate discount based on transactionType. 
    
    TransactionType is BANK_TRANSFER, so the discount value is: 5%. For given transaction, the discount will be charged: 
    **10-(10 * 0,05) = 9.50** <= it's the new value of transaction amount. 
   
2. Calculate discount based on transactionDate. 
   Transaction date took place in last day from processingDate so APP charge the discount: 
**9.50 - (9.50 * 0,05) = 9,03** <= the final amount for client charging. 
  
## 2. Summary and result
Next step is preparing a summary and response. App build the summary from calculated transactions. The list of results
is being prepared per tenant. Each of tenant part has split results based on transactionType and last part is summary of
all tenant transactions.

Response e.g.
```javascript
[
  {
    "tenantId": "123456",
    "_data": [
      {
        "noOfTransactions": 1,
        "baseCharge": 100,
        "chargeWithDiscount": 93.1,
        "transactionType": "MOBILE_TRANSFER"
      },
      {
        "noOfTransactions": 2,
        "baseCharge": 20,
        "chargeWithDiscount": 18.53,
        "transactionType": "BANK_TRANSFER"
      },
      {
        "noOfTransactions": 1,
        "baseCharge": 10,
        "chargeWithDiscount": 9.41,
        "transactionType": "CASH"
      },
      {
        "noOfTransactions": 4,
        "baseCharge": 130,
        "chargeWithDiscount": 121.04,
        "transactionType": "ALL"
      }
    ]
  },
  {
    "tenantId": "978621",
    "_data": [
      {
        "noOfTransactions": 1,
        "baseCharge": 12,
        "chargeWithDiscount": 11.18,
        "transactionType": "MOBILE_TRANSFER"
      },
      {
        "noOfTransactions": 2,
        "baseCharge": 28,
        "chargeWithDiscount": 26.03,
        "transactionType": "BANK_TRANSFER"
      },
      {
        "noOfTransactions": 1,
        "baseCharge": 12,
        "chargeWithDiscount": 11.29,
        "transactionType": "CASH"
      },
      {
        "noOfTransactions": 4,
        "baseCharge": 52,
        "chargeWithDiscount": 48.5,
        "transactionType": "ALL"
      }
    ]
  }
]
```

---

## Demo

For running application just run it on Intellij or by maven command - like a simple SpringBoot APP. It will run on port:
8080.

For the demo and to see the results on the browser, you can hit on below endpoint:
`http://localhost:8080/consumeTransaction`

Demo data is loading from [LoadDataService](https://github.com/mlepecki/recruitmentAppQA/blob/main/src/main/java/com/sm/recruitment/app/services/LoadDataService.java) and can be modifying.

---

## QA TASKS

1. Define the edge-cases
2. Prepare the unit tests for each:
    - service
    - controller
    - processor in the project.
