# How to report on issue\bug: #
_What you should do to keep bug assignee happy:)_
### 1. Verify if bug REALLY exists ###
It should be reproducible in some conditions every time.


### 2. Describe bug ###
Answer three questions as fully as possible. Your describtion should be very strict. If you use some specific function or entity, make references to it full enough to identify it.

  * What effect on system state you've tried to achieve?
  * What steps you've taken to achieve that effect?
  * What effect on system state you've got? What is wrong?


### 3. Collect debug info ###
Get info from all sections of system. Our system contains:
  * UI flex part
  * Java web-application
  * Database

Files revision is important debug info too!

Lot's of debug info could be gathered from server.log of our application server. Usually info from it and steps to reproduce is enough to identify bug. In hard cases you should to inspec your db state.

Try if bug is reproducible at other environments (In our case - web browsers)

### Manually run server request and check if there any errors ###
When service ContragentSvc recieves such data:
```
mode=(EDIT)
name=(test)
child=(1)
```

Actually such request is made
```
http://localhost:8080/bakery/svc.htm?svc=org.bakery.server.controllers.svc.impl.ContragentSvc&mode=EDIT&name=test&child=1
```

If you run it from your web browser you'll find that mandatory attribute 'address' is not passed.

Sometimes there's no errors neither at server.log nor at web part - maybe there's no error at all?

### 4. Try to fix it yourself ###
  * Update your java and flex parts, redeploy them
  * Check if redeployment process was successful for java part and 'Problems' tab contains no records for Flex part.
  * Inspect server.log and try to find human readable statements
  * Database errors like 'column xxx is not fould' could be caused by not proper DB schema - try to update it too

## Examples ##
### BAD bug report ###
```
Something is not working here:
http://localhost:8080/bakery/svc.htm?svc=org.bakery.server.controllers.svc.impl.ContragentSvc&mode=EDIT&name=test&child=1
```


### GOOD bug report ###
```
What effect on system state you've tried to achieve?
I'm trying to start my MoneyMoves tab (admin/MoneyMoves.mxml r120


What steps you've taken to achieve that effect?
I'm opening flex application using Flex builder 'Run application' action from menu, which is displayed on right mouse click on 'Bakery.mxml (r119)'.


What effect on system state you've got? What is wrong?
My moneyMoves tab signals 'Contragent name could not be null' every time at tab startup. But no entity update actions taken, thus validation should not be triggered.

Debug Info

At startup, tab sends such data:
mode=(FETCH)

To service:
org.bakery.server.controllers.svc.impl.MoneyMoveSvc

Reproducible at all browsers (Mosilla, IE)

Server.log contains no error data (It's attached to this issue)
```