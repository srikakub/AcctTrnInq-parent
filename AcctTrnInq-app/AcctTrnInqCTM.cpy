       01   AcctTrnInqCTMReq.
       05   ServiceRequest         PIC X(24).
       05  InputHeader.
        10  ServiceRequest2        PIC X(24).
        10  VersionNumber          PIC 9(02).
        10  EffectiveDate          PIC 9(08).
        10  InitiatedDate          PIC 9(08).
        10  InitiatedTime          PIC 9(08).
        10  InitiatingCo           PIC 9(05).
        10  Operator               PIC X(08).
        10  OperatorBranch         PIC 9(06).
        10  WorkstationId          PIC X(08).
        10  OriginatingApplication PIC X(03).
       05   SecondaryHeader.
        10  ProcessingApplication  PIC X(03).
        10  AccountNumber          PIC X(23).
        10  RegistrationNumber     PIC X(23).
        10  LinkageNumber          PIC 9(02).
       05   ServiceRequestMsg.
        10  NoToRetrieve           PIC 9(2).
        10  NextTransactionStart   PIC 9(6).
        10  TransactionProcessDate PIC 9(08).
       01  AcctTrnInqCTMRsp.
       05  ServiceName             PIC X(24).
       05  OutputHeader.
        10  ServiceResponse        PIC X(24).
        10  VersionNumber          PIC 9(02).
        10  DataLength             PIC 9(08).
        10  ServiceResultCode      PIC 9(03).
        10  ErroMessage            PIC X(40).
        10  ConditionCode          PIC 9(08).
       05 SecondaryHdr.
        10  ProcessingApplication  PIC X(03).
        10  AccountNumber          PIC X(23).
        10  RegistrationNumber     PIC X(23).
        10  LinkageNumber          PIC 9(02).
       05  ServiceResponseMsg.
        10  NextTransactionStart   PIC  9(06).
        10  NumberOfTransactions   PIC  9(02).
        10  OutputDetails OCCURS 50 TIMES.
          15  TransactionDateV2       PIC  9(08).
          15  TransactionTimeV2       PIC  9(06).
          15  TransactionSRCECODEV2   PIC  X(02).
          15  TransactionSRCEV2       PIC  X(20).
          15  TransactionAMTV2        PIC  9(11)V99.
          15  TransactionCRINDV2      PIC  X.
          15  TransactionTypeCodeV2   PIC  9(02).
          15  TransactionTypeV2       PIC  X(20).
          15  TransactionBranchV2     PIC  9(04).
          15  TransactionNarrativeV2  PIC  X(40).
          15  TransactionSerialNoV2   PIC  9(13).
          15  TransactionSourceV2     PIC  X(04).
          15  TransactionEffDateV2    PIC  9(08).