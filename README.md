# azure-handson
Azure handson 3 week Online Live Training for O'Reilly

# Azure Virtual Machines

# Azure App Service

## Step 1: Create an empty/default Python app on Azure. 

Azure App Service provides us with a capability of creating a default Python App on Azure App Service. 

There are a bunch of ways in creating this default Python App - we use Portal and CLI in our case.

## Creating a Python Web App using Console

Follow the course instructions to get this started

## Creating a Python Web App using CLI

The easiest way to create the Python Web App is to issue the command: `az webapp up` from your code folder. Based on the code stack you have in the folder (repo), Azure will deduce the runtime stack (if your code is a Python code repo, it will set the runtime stack as Python and so on), assigns the defaults for rest of them. 

Let's fetch the sample Python code I have in the [Azure Handson Python](https://github.com/madhusudhankonda/azure-handson-python) repo, as discussed in the next step.

### Checkout Python Git Repo

Checkout the azure-handson-python codebase. 

`
git clone https://github.com/madhusudhankonda/azure-handson-python.git
`
Change to the code directory:

`
cd azure-handson-python
`
Now your sample code is ready, let's create an empty appservice for Python runstack. 

### Create Python App

Azure will let us know the sensible defaults that it would apply when we run `az webapp up --dryrun` - the dryrun flag wouldn't create the resources but for our information purposes only. Let's go with this route for now:

Once moved to azure-handson-python folder, issue the following command without the `dryrun`:

`az webapp up`

This command would create all the required configuration for our Python App. It would take a minute or two to get this resource created. 
