# IntelligentWikiEditor
## What is it?
I'm trying to create tools to simplify creating and modifying articles for [Wikipedia](http://en.wikipedia.org).
I call it **wiki editor** and it is text editor platform, optimized for operations with Wikipedia articles source
language - [Wiki markup](http://en.wikipedia.org/wiki/Help:Wiki_markup) with some _intelligent_ possibilities.

IntelligentWikiEditor is open-source software licensed under the
[GPLv2](http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html).

Nowadays, project is under active development and can **NOT** be used as final product. But you still can checkout
the source and build it manually. Note, that Java 8u40 or later is required.

(c) Rudnytskyi Myroslav, 2014 - 2016

[![Join the chat at https://gitter.im/mrudnytskyi/IntelligentWikiEditor](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/mrudnytskyi/IntelligentWikiEditor?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

## Installation guide
### Java version check
First of all, verify that necessary Java version is installed. To get java version, try following command in a
terminal window:
```
java -version
```
Note, that JRE 8u40 or later is required, so if your version is older, download latest JRE version
[here](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html).

### Get binaries
> Note, that there are no releases yet!

Download zip file with latest release and extract it to any folder.

### Execute
Double-click `editor.jar` to start _IntelligentWikiEditor_. If this does not work, try following command in a
terminal window:
```
java -jar editor.jar
```

## Features and TODOs
* [x] Multiple platforms support: web application and Windows, Linux and Mac apps (partly, only Windows app only)
* [x] Integrated Design Environment (IDE) based graphical-user interface (GUI)
* [x] Simple text editor functionality with `New`, `Open file`, `Open URL`, `Save`, `Save as`, `Close`, `Close all`
actions (partly, no close)
* [x] :gem: Simple text area with `Cut`, `Copy`, `Paste`, `Select all` and `Undo/redo` actions with hot keys (partly,
no undo/redo yet)
* [ ] Local editing history
* [ ] :gem: Rich text area with extended text selection, manipulation, navigation and so on actions (delete line with hot key, etc)
* [x] :gem: Rich IDE area with syntax highlighting, wiki-text fragments folding and line numbers showing (partly, only line
numbers implemented)
* [ ] Offline (without Internet connection) work support for auto-complete functions
* [x] Insert wikipedia link (on another article) with auto-complete functionality
* [x] Insert external link functionality
* [x] Insert template (with parameters) with auto-complete functionality
* [x] Insert headings functionality
* [ ] :gem: Insert category with auto-complete functionality
* [ ] Insert image with auto-complete functionality
* [ ] Insert snippet (text with source) functionality
* [ ] Insert source (book, etc) with auto-complete functionality
* [ ] Automatic image, templates and categories search
* [ ] :gem: Automatic wiki links creation ([wikification](https://en.wikipedia.org/wiki/Wikification))
* [x] :gem: AST preview with `Filter`, `Go to`, `Delete` actions (partly, just view)
* [ ] Enabling many tabs editing
* [x] Internationalization support (partly, just english and ukrainian)
* [ ] Application settings
* [ ] Implement user guide and context help
* [ ] Automatic article stub generating (see [first version](https://github.com/mrudnytskyi/WikiBot) for details)
* [ ] :gem: Article preview
* [ ] :gem: Article inspections and AST transformers
* [ ] :gem: Spell checker for article text
* [ ] Article wiki-text formatting
* [ ] :gem: Smart search and replace
* [ ] Show special symbols functionality
* [ ] Recent opened files history
* [ ] Toolbar with often used actions and statusbar with article text statistics
* [ ] Bookmarks manipulating
* [ ] TODOs manipulating

## Credits
* [Spring](https://spring.io) Framework [IoC](https://en.wikipedia.org/wiki/Inversion_of_control) container
used to produce maintainable code
* Module [Sweble Wikitext](https://github.com/sweble/sweble-wikitext) provides parser for MediaWiki's wiki-text
* Libraries [RichTextFX](https://github.com/TomasMikula/RichTextFX) and
[ControlsFX](http://fxexperience.com/controlsfx/) were used to create perfect GUI
* Framework [JUnit](http://junit.org) proves that anything in the project works as expected :bug:
* Programming language [Groovy](http://www.groovy-lang.org) deals with JSON and XML parsing
* Framework [Commons Validator](https://commons.apache.org/proper/commons-validator/) helps to validate input data
* Library [Xstream](http://x-stream.github.io) used to simplify work with XML files
