# Swim Set Planner Site Backend

## Description

Backend site for my swim set planner that will be deployed to AWS.

## ECR Instructions

These instructions are meant for PowerShell using the AWS Tools for PowerShell. If you don't have those, give these commands whirl.
```
> Install-Module -Name AWS.Tools.Installer
> Install-AWSToolsModule AWS.Tools.ECR -CleanUp
```

Log in to AWS using the SSO link in the parent project. Once you have done so, go to your access keys. In the PowerShell options, copy **ALL** the suggested environment variables. The command should look like this (don't remove the quotes).
```
$Env:AWS_ACCESS_KEY_ID="ACCESS KEY ID"
$Env:AWS_SECRET_ACCESS_KEY="SECRET ACCESS KEY"
$Env:AWS_SESSION_TOKEN="SESSION TOKEN"
```

From there, log in using Docker.
```
(Get-ECRLoginCommand).Password | docker login --username AWS --password-stdin 485701710773.dkr.ecr.us-east-1.amazonaws.com
```

Run a Gradle build and then create a Docker image.
```
gradlew clean build
make-docker-image.bat
```

If we want to run the application one last time locally before sending it on its way to AWS ECR, we can run
```
run-docker-image.bat
```

Otherwise, off to AWS the image goes when these commands are ran!
```
docker tag swimsetplannerbackend:latest 485701710773.dkr.ecr.us-east-1.amazonaws.com/swim-set-site-spring-backend:latest
docker push 485701710773.dkr.ecr.us-east-1.amazonaws.com/swim-set-site-spring-backend:latest
```

## Troubleshooting

Troubleshooting steps.

### AWS

To login to AWS using Docker, you might get the following errors:
```
(Get-ECRLoginCommand).Password
Get-ECRLoginCommand : The security token included in the request is invalid.
At line:1 char:2
+ (Get-ECRLoginCommand).Password
+  ~~~~~~~~~~~~~~~~~~~
    + CategoryInfo          : InvalidOperation: (Amazon.PowerShe...inCommandCmdlet:GetECRLoginCommandCmdlet) [Get-ECRLoginCommand], InvalidOperationException
    + FullyQualifiedErrorId : Amazon.ECR.AmazonECRException,Amazon.PowerShell.Cmdlets.ECR.GetECRLoginCommandCmdlet
```
or
```
Get-ECRLoginCommand : The security token included in the request is invalid.
At line:1 char:2
+ (Get-ECRLoginCommand).Password | docker login --username AWS --passwo ...
+  ~~~~~~~~~~~~~~~~~~~
    + CategoryInfo          : InvalidOperation: (Amazon.PowerShe...inCommandCmdlet:GetECRLoginCommandCmdlet) [Get-ECRLoginCommand], InvalidOperationException
    + FullyQualifiedErrorId : Amazon.ECR.AmazonECRException,Amazon.PowerShell.Cmdlets.ECR.GetECRLoginCommandCmdlet
```
In the past, this means you didn't set your session ID when you set up your environment variables from your access keys on AWS. Do this.

You might also get
```
Get-ECRlogincommand : No credentials specified or obtained from persisted/shell defaults.
At line:1 char:1
+ Get-ECRlogincommand
+ ~~~~~~~~~~~~~~~~~~~
    + CategoryInfo          : InvalidOperation: (Amazon.PowerShe...inCommandCmdlet:GetECRLoginCommandCmdlet) [Get-ECRLoginCommand], InvalidOperationException
    + FullyQualifiedErrorId : InvalidOperationException,Amazon.PowerShell.Cmdlets.ECR.GetECRLoginCommandCmdlet
```
This just means you did not run the command to set you credentials.
