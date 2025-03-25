# Angular Frontend

Angular site that will call a backend hosted on AWS to manage my Swim Calendar.

## Docs

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 17.3.8.

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

In the future, running `ng e2e` will execute end-to-end tests via a platform of my choice. I'll need to first add a package that implements end-to-end testing capabilities.

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI Overview and Command Reference](https://angular.io/cli) page.

## Host Information

This Angular site is hosted an AWS S3 bucket.

This is hosted on the AWS account linked in the [AWS Docs](../docs/aws-docs.md).

Bucket Information:
 * Name: `swim-set-static-angular-site`
 * ARN: `arn:aws:s3:::swim-set-static-angular-site`
 * [Site](http://swim-set-static-angular-site.s3-website-us-east-1.amazonaws.com/sets)

Eventually, this will be accessed through AWS Amplify.