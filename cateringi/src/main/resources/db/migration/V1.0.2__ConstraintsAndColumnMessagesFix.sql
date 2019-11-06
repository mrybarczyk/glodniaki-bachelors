ALTER TABLE Messages
DROP CONSTRAINT MessagesCompanyData

ALTER TABLE Messages
DROP CONSTRAINT MessagesUserDataForeignKey

exec sp_rename 'Messages.CompanyDataID','Sender'

exec sp_rename 'Messages.UserDataID','Receiver'

ALTER TABLE Messages
ADD CONSTRAINT MessagesAccountsSenderForeignKey FOREIGN KEY(Sender)
REFERENCES Accounts(AccountID)

ALTER TABLE Messages
ADD CONSTRAINT MessagesAccountsReceiverForeignKey FOREIGN KEY(Receiver)
REFERENCES Accounts(AccountID)

ALTER TABLE Messages
ADD DateSent SMALLDATETIME