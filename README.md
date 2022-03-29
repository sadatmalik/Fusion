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
schema and stored procedure:

```bash
fusion_db_schema.sql
fusion_stored_procedures.sql
```

Compile and run Fusion Command Line:

## Usage

```python
import foobar

# returns 'words'
foobar.pluralize('word')

# returns 'geese'
foobar.pluralize('goose')

# returns 'phenomenon'
foobar.singularize('phenomena')
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)