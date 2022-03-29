# Fusion - Command Line

Fusion is a command line based personal finance management system backed by a MySQL
database.

## Installation

You will need to start an instance of MySQL listening on the default MySQL port 3306. 

Next, clone the [git](https://github.com/sadatmalik/Fusion) repository:

```bash
git clone https://github.com/sadatmalik/Fusion.git
cd Fusion
```

Execute the following SQL scripts found in the Fusion directory to create the required DB 
schema, stored procedures, and some sample data for demo purposes:

```bash
fusion_db_schema.sql
fusion_mysql_scripts.sql
fusion_stored_procedures.sql
```

Open the project in your IDE. Ensure the following MySql connector jar file is present on
the classpath:

```bash
mysql-connector-java-8.0.26.jar
```

From your IDE you can now run src/FusionMain.

## Brief

Fusion Command Line is a  simple command line based utility for managing basic personal
finances. To begin create a user and set up some income and expense details.

Its primary purpose was to essentially model the data relationships that would comprise 
the basis of the full scale Fusion Web application, within the Fusion suite.

While some simple usage instructions follow - for a full-featured application please
see Fusion Web.

## Usage

Select what you want to do using the available menu options.

```
Welcome to Fusion. Please select from users below:

1. Create new user
```

Fusion main menu:

```
Fusion Main Menu. What would you like to do?

1) View Quick Stats
2) Add/Edit Income & Expenses
3) Run Reports
4) Create/View Custom Goals
5) Exit
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what 
you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](license.txt)