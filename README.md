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

## Access the NodeJS app

In this example, we would like to create a simple WebServer using NodeJS and get it deployed to a newly created webapp. The source code for this NodeJS app is available in [azure-handson-node](https://github.com/madhusudhankonda/azure-handson-node) repository.

We can deploy the WebApp using Azure Portal or use CLI. Follow the steps outlined in the above sections to do this. Don't forget to add the deployment section and link it to the repository (fork the above repo and create your own) that consists of your code. This is shown in the figure below:
![image](https://user-images.githubusercontent.com/1698230/225875937-61d68cda-30c6-4ba5-b272-e80040c47cb2.png)

> Link your repository to the WebApp for continuous deployment

You can also use your local Git should you wish to deploy from your local machine, in addition to other options as shown in the image below:
![image](https://user-images.githubusercontent.com/1698230/225876359-a1c8c75f-a85e-43eb-902d-7500bc0e45c4.png)

You can link/unlink the code repository by visiting the WebApp and navigating to Deployment -> Deployment Center section. 

##############################

# ARM Templates

Templates help code and version our infrascture - this falls in infrastucture-as-code (IAC) category model. The idea is to create a JSON file with appropriate schema, version and required resources, get it deployed by using Azure CLI or PowerShell.

The following json file is the one that creates a sample resource group:

```
{
    "$schema": "https://schema.management.azure.com/schemas/2018-05-01/subscriptionDeploymentTemplate.json#",
    "contentVersion": "1.0.0.1",
    "variables": {},
    "resources": [
        {
            "type": "Microsoft.Resources/resourceGroups",
            "apiVersion": "2018-05-01",
            "location": "southuk",
            "name": "rg-arm",
            "properties": {}
        }
    ],
    "outputs": {}
}
```
As you can see, the template file has a schema, version, variables, rosources and outputs. 

> When you create any resource via Portal, at the final step before creating, Portal provides a chance to download the template for that file - this template consists of relevant instructions to Azure Resource Manager to execute those instructions, as shown in the following image:

<img width="540" alt="image" src="https://user-images.githubusercontent.com/1698230/225843770-60321bf4-cd02-4ea0-9603-87e50ec17d97.png">

## Templates with parameters

The above template has hard coded values such as `location` and `name` in the template itself. While this is permissable, not extensible. 

We can instead enhance the template file by providing the parameters. The [no-params-file](https://github.com/madhusudhankonda/azure-handson/blob/main/arm-templates/new-resource-no-params.json) is available in the repository for your reference.

Check the next section how we can pass the parameters to CLI

## ARM Templates Editor

You can use any text editior for creating your tempaltes.I find Visual Studio Code with its Azure Resoruce Manageter Templates extension helps here a lot if you are a fan of VSC. Search and install the ARM templates extension in your VSC:
<img width="688" alt="image" src="https://user-images.githubusercontent.com/1698230/225844555-a11c3ff2-0cff-40da-b686-7d521c1a522b.png">

When your template is ready, go to the integrated terminal from VSC or even your own terminal and deploy the template (make sure you are logged in. If not login by invoking `az login` command).

Once login was successful, issue the following command to get your resoruce template deployed to Azure (the `new-resource-template.json` is your template file - make sure you provide the correct path of the file):

```
az deployment group create \
  --name my-arm-template-rg \
  --resource-group olt-arm \
  --template-file new-resource-template.json \
  --location "southuk"
  --name "my-new-rg"
```

Running this command will get the resource group created by deploying the template. The parameters are passed in via the approprate options like `--location` and `--name`. 

> The sample template file is available here in my [azure-handson](https://github.com/madhusudhankonda/azure-handson/blob/main/arm-templates/new-resource-template.json) repo.

## Template Library

> Templates will be deprecated by 2025 in favour of Template Specs - I'm covering both here for your convenience.

### Templates

Azure Portal provies a "Templates" feature (preview feature, mind you!) that you would be able to create and manage your templates.

Search for "Templates" in the site search bar - select Templates feature. You'd be navigated to Templates home. 

Click on the Create button to get the Template created - provide a name and description. In the ARM Template tab, provide the JSON for your resource creation (copy the resource that we had created using the json shown earlier on). 

Once you save this, your templates gets added to the Template library. 

You can deploy by clicking on that template which leads you to a screen where you can click on Deploy button to get this resource template executed. 
![image](https://user-images.githubusercontent.com/1698230/225879834-54e5c656-1d71-4418-92eb-30b8eda0ac33.png)

Click on the Create button to get the Template created - provide a name and description. In the ARM Template tab, provide the JSON for your resource creation (copy the resource that we had created using the json shown earlier on). 

### Template Specs

Searching for "Template Specs" in the site search bar will lead you to the Template Specs service. This service allows you to import already created template (you may have downloaded during your resource creation!) or create a new one. 

Let's create a new spec by click on the "Create template spec" menu item. This will lead us into a similar form where we were using for creating various resources. Provide the necessary (self-explanatory) details here and go foward to the "Edit template" tab. 

In the Edit Template tab, code your spec or simply paste and modify your existing spec. Once the filling up of the spec definition is completed, click on the Create button to get this template instantiated. The template is ready to be deployed once the validation is completed.

You can deploy it by clicking on the template itself which leads to a details page with "Deploy" button enabled. Clicking the Deploy button will let your resource deployed!
