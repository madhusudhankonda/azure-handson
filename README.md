# Azure Handson 3 Week Online Training 
This notes here corresponds to the Azure handson 3 week Online Live Training for O'Reilly that I regularly teach. Though I try to present as much info as possible here, I do suggest you to join the course to follow along my instructions on bits that I coudln't manage to get them documented here. 

# Azure CLI

To use the Azure CLI client, we have two options - one is to use our own terminal (my instructions are Linux/MacOS based Operation Systems - please check out the official documentation for Windows) and the other is to use CloudShell. 

## Terminal

Open the terminal on our local Linux/MacOS machine. First thing to do is to login to our Azure account. We can issue `az login` at the command line that'll get the login sorted provided we already logged in via browser earlier on. If you havne't logged in to portal, do so by visiting [Azure Portal](https://portal.azure.com) and logging into it. 

Once you are in Portal, head over to the terminal and issue the following command:
```
az login
```

This will automatically opens up a webbrowser asking you to pick an account, as shown here:
```
A web browser has been opened at https://login.microsoftonline.com/organizations/oauth2/v2.0/authorize. Please continue the login in the web browser. If no web browser is available or if the web browser fails to open, use device code flow with `az login --use-device-code`.
```

On the opened browser's tab, pick the email/account you've used to login to the portal earlier. It would authenticate you and let you in on the cli (you should see a JSON response with few details of your account).

## Cloud Shell

Opening a Cloud Shell usually associates our login credentials. If not, do refer to Azure Power Shell documenation. 

# Azure Virtual Machines

Azure Virtual machines can be created multiple ways - we look at couple of ways here - via Portal and Azure CLI.

## Creating a VM using Portal

Follow the class to perform actions for creating the VM via Portal

## Creating a VM using CLI

The Azure CLI can be used to create a VM of our choice. It's a two step process - first we need to create a resource group (if not already exists) and then create a VM using the `az vm create` command.

So let's create the resource group first:

```
az group create --name "rg-vm-cli-group" --location "uksouth" 
```

Executing this command will create a required resource group for us with the "rg-vm-cli-group" name. 

> If you wish to change the location, but wouldn't know the short name for your location, exeucte the following command `az account list-locations -o table` which brings back all the geo locations and their shortnames in a table format.

Now that we have our resource group created (you can issue `az group list` to fetch all the available resource groups), let's create a VM.

We use `az vm create` command to create our VM - this command accepts a few options such as resoruce group, name, ip address and others. Let's create ours with just the name, resource group and an image:

```
az vm create --resource-group "rg-vm-cli-group" --name "my-sample-vm" --image "UbuntuLTS"
```
We provided an "UbuntuLTS as the image in the above script. The following are the common list of images available:

```
...
'CentOS'
'Debian'
'UbuntuLTS'
'Win2022Datacenter'
'Win2022AzureEditionCore'
'Win2019Datacenter'
...
```

## Check the configuration of the VM

You can find various bits of configuration about this VM on Portal. Let's open a port 80 on this machine:

```
az vm open-port --port 80 --resource-group "rg-vm-cli-group" --name "my-sample-vm"
```

## Installing a NGNIX webserver using Portal

The VM that deployed was pretty barebones. Let's install Ngnix webserver on it using "Run Command". 

On the VM's menu, head over to "Run Command" under "Operations". Input the following command:

```
sudo apt-get update
```

This command will update the softwared on the VM. 

Once this is done, execute this command:

```
sudo apt-get install -y nginx
```
This command will execute the nginx webserver on the VM.

Once the installation is complete, visit the IP address of the VM to access the Nginix server

## Installing the NGINX server using CLI

We can install software by executing `run-command` command on the `vm` service. The following command would run the installation for us:

```
az vm run-command invoke  \
  --resource-group "rg-vm-cli-group" \
  --name "my-sample-vm" \
  --command-id RunShellScript \
  --scripts "sudo apt-get update && sudo apt-get install -y nginx"
```

Accessing the IP address should show you the Nginx welcome page!

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

This command would create all the required configuration for our Python App. It would take a minute or two to get this resource created. You should see an output something like this:

```
➜  azure-handson-python git:(main) az webapp up
The webapp '.....' doesn't exist
Creating Resource group '....' ...
Resource group creation complete
...
--name/-n default: vxxxxx-xxxxxx
{
  "URL": "http://xxxxx-xxxxxx.azurewebsites.net",
  "appserviceplan": "xxxxxxxxx_asp_1869",
  "location": "eastus",
  "name": "xxxxx-xxxxxx",
  "os": "Linux",
  "resourcegroup": "xxxxxxxxx_asp_1869",
  "runtime_version": "python|3.10",
  "runtime_version_detected": "-",
  "sku": "PREMIUMV2",
  "src_path": "//Users//mkonda//DEV//OLT//AZURE_HANDSON//azure-handson-python"
}
```

As you can see, the Python App was created for us with sensible defaults (note that Azure chose Premium size plan). We can instead provide our own details. The following command is an updated command with custom details:

```
az webapp up --name olt-webapps-test-123 --sku B1 --location eastus --resource-group rg-olt-apps-123
```
Executing this command would create the required resoruces and most importantly the Python app will be deployed and ready to be accessed.
> Check it out this document for various command line options for webapp command, including various SKUs: [Azure WebApp CLI reference](https://learn.microsoft.com/en-us/cli/azure/webapp?view=azure-cli-latest#az-webapp-up)


## Access the Python App

Now that we've managed to create the App, let's check it out.

On your WebApp (in Portal, you should find a "Default domain" (on the right hand side) for our application, as you can see in this image:
![image](https://user-images.githubusercontent.com/1698230/224291687-1be56706-4423-41c2-b845-57e14686b9e0.png)

Clicking this link will take our newly deployed App!

