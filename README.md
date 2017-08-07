# Concordance

A concordance is a table that tells how often a word appears in a text (or where in a text it appears; but I will not be using this definition). This library instead will:

* Read a UTF-8 text, which can contain elements such as punctuation.
* Count the frequency of words in the text
* Sort the words either alphabetically or by frequency
* Return and/or print the words and their corresponding frequency

The ordering:

* **Alphabetical** - Will sort the words based on the locale provided.
* **By Frequency** - Will sort from the most frequent to the least frequent. If two words have the same frequency, they will be sorted alphabetically.


## Getting Started

** Features **
  * All languages supported by UTF-8 can be used with this Concordance.
  * The concordance will sort the words based on the locale provided.
  * All UTF-8 characters supported even those after the BMP.
  * This concordance takes in a java.io.Reader, which means the library can parse anything from text files http streams.

** Internal Improvements **
  * Migrate to a multithreaded implementation
  * Utilize MutableInt for HashMap to improve write times for already existing words
  * Optimize unicode character checking


** Unsupported Features**
  * Currently, apostophes and hyphenated english words will be broken at the punctiation mark.  This is because the library is designed to work with all languages supported with UTF-8.


### Prerequisites

Java version at least 1.8.0_31 to use the library.
Junit 4.12 to run the unit tests.


## Running the tests

You will need Junit in order to run the tests. As of writing this, the test successfully compile and run with Junit 4.12.


### Break down into end to end tests

There are two test suites and 8 test files.
* **Unit Tests** - Tests the public functions using various input from upper unicode values (above BMP) to various languages and their punctiaton.
* **Stress Tests** - Tests are library against large files with a large breadth of words and a large set of repeating words.


## Authors

* **Spencer Reeves** - *Initial work* 


## License

This project is licensed under the MIT License.
