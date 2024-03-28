opa-demo
================================
Open Policy Agent demo application



**Overview**
This Spring Boot Java application is related to a simple implementation of OPA(Open Policy Agent) 
opa-demo has one endpoint expecting a parameter. This parameter is sent to the OPA and based on its value the OPA makes a decision, which is later the response.


OPA server can be accessed on the following URL:
  - http://localhost:8181/



OPA policies can be accessed on the following URL:
  - http://localhost:8181/v1/policies/authentication_policy/resource_access_policy

    

opa-demo can be accessed on the following URL:
  - http://localhost:8080/user/{} (param required)



**Getting Started**

1. Install Docker
2. Clone the repository to your local machine.
3. Run the necessary command(s) in the terminal.

-  To run OPA on port 8181, use the command:
    ` docker run -p 8181:8181 openpolicyagent/opa run --server ` 

4. Launch the app and visit http://localhost:8080/user/{} (param required)
   
**Params: 1 - standard, 2 - pro, 3 - premium, any diff number gives unknown**

   
**IMPORTANT**
These are purely base examples that may need additional configurations for production use.


**License**
This project can be freely used :) 
