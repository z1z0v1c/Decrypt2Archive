# Decrypt2Archive

![GitHub Workflow Status](https://img.shields.io/github/actions/workflow/status/z1z0v1c/Decrypt2Archive/maven.yml?branch=master)
![GitHub Last Commit](https://img.shields.io/github/last-commit/z1z0v1c/Decrypt2Archive)
![GitHub issues](https://img.shields.io/github/issues/z1z0v1c/Decrypt2Archive)
![License](https://img.shields.io/github/license/z1z0v1c/Decrypt2Archive)

Decrypt2Archive is a Java-based client-server application for decrypting and archiving files. It uses sockets for communication (java.net.ServerSocket and java.net.Socket) and processes .txt and .xls/.xlsx files encrypted with a provided encryption suite. Decrypted files are compressed into an archive and stored in a specified output directory.

## Features

- Client-server architecture using `java.net.ServerSocket` and `java.net.Socket`
- Text-based communication protocol (no object serialization)
- Decrypts files using the provided encryption logic
- Supports `.txt` and `.xls/.xlsx` files
- Compresses decrypted files into a single zip
- Logs all actions into the database

## Usage

### Run Server

```bash
java -jar server_2.jar -p <portNumber> -d <pathToDatabase> -o <pathToOutputDir>
```

- `portNumber`: Port for the server to listen on
- `pathToDatabase`: Path to the SQLite database (e.g., `./database.sqlite3`)
- `pathToOutputDir`: Destination directory for the output file

### Run Client

```bash
java -jar client_2.jar -s <serverAddress> -p <portNumber> -i <pathToInputDir>
```

- `serverAddress`: Address of the server machine
- `portNumber`: Port number the server is listening on
- `pathToInputDir`: Folder with encrypted input files

> **Important:** Start the server before launching the client.

## Communication Protocol

The client and server communicate using a text-based protocol. The protocol involves the following steps:

1. Client connects to the server
2. Client sends information about files to be processed
3. Server acknowledges and begins processing
4. Server sends final result (success/failure)
5. Connection is closed

## Database Schema

The SQLite database contains the following tables:

1. `aes_keys` - Stores decryption keys for files
   - `key`: Decryption key
   - `file`: File names

2. `actions` - Records all system operations
   - `id`: Primary key
   - `action`: Type of operation performed
   - `date`: Time of the operation

## License

This project is licensed under the MIT License. Provided for educational purposes, based on a task by msgNETCONOMY. See the [LICENSE](https://github.com/z1z0v1c/Decrypt2Archive/LICENSE) file for details.
