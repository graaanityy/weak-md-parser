package com.github.arena.challenges.weakmdparser;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MarkdownParserTest {

    private MarkdownParser markdownParser;

    @BeforeEach
    public void setup() {
        markdownParser = new MarkdownParser();
    }

    @Test
    public void normalTextAsAParagraph() {
        String input = "This will be a paragraph";
        String expected = "<p>This will be a paragraph</p>";

        assertEquals(expected, markdownParser.parse(input));
    }

    @Test
    public void italics() {
        String input = "_This will be italic_";
        String expected = "<p><em>This will be italic</em></p>";

        assertEquals(expected, markdownParser.parse(input));
    }

    @Test
    public void boldText() {
        String input = "__This will be bold__";
        String expected = "<p><strong>This will be bold</strong></p>";

        assertEquals(expected, markdownParser.parse(input));
    }

    @Test
    public void normalItalicsAndBoldText() {
        String input = "This will _be_ __mixed__";
        String expected = "<p>This will <em>be</em> <strong>mixed</strong></p>";

        assertEquals(expected, markdownParser.parse(input));
    }

    @Test
    public void withH1HeaderLevel() {
        String input = "# This will be an h1";
        String expected = "<h1>This will be an h1</h1>";

        assertEquals(expected, markdownParser.parse(input));
    }

    @Test
    public void withH2HeaderLevel() {
        String input = "## This will be an h2";
        String expected = "<h2>This will be an h2</h2>";

        assertEquals(expected, markdownParser.parse(input));
    }

    @Test
    public void withH6HeaderLevel() {
        String input = "###### This will be an h6";
        String expected = "<h6>This will be an h6</h6>";

        assertEquals(expected, markdownParser.parse(input));
    }

    @Test
    public void unorderedLists() {
        String input = "* Item 1\n* Item 2";
        String expected = "<ul><li>Item 1</li><li>Item 2</li></ul>";

        assertEquals(expected, markdownParser.parse(input));
    }

    @Test
    public void aLittleBitOfEverything() {
        String input = "# Header!\n* __Bold Item__\n* _Italic Item_";
        String expected = "<h1>Header!</h1><ul><li><strong>Bold Item</strong></li><li><em>Italic Item</em></li></ul>";

        assertEquals(expected, markdownParser.parse(input));
    }

    @Test
    public void markdownSymbolsInTheHeaderShouldNotBeInterpreted() {
        String input = "# This is a header with # and * in the text";
        String expected = "<h1>This is a header with # and * in the text</h1>";

        assertEquals(expected, markdownParser.parse(input));
    }

    @Test
    public void markdownSymbolsInTheListItemTextShouldNotBeInterpreted() {
        String input = "* Item 1 with a # in the text\n* Item 2 with * in the text";
        String expected = "<ul><li>Item 1 with a # in the text</li><li>Item 2 with * in the text</li></ul>";

        assertEquals(expected, markdownParser.parse(input));
    }

    @Test
    public void markdownSymbolsInTheParagraphTextShouldNotBeInterpreted() {
        String input = "This is a paragraph with # and * in the text";
        String expected = "<p>This is a paragraph with # and * in the text</p>";

        assertEquals(expected, markdownParser.parse(input));
    }

    @Test
    public void markdownUnorderedListsCloseProperlyWithPrecedingAndFollowingLines() {
        String input = "# Start a list\n* Item 1\n* Item 2\nEnd a list";
        String expected = "<h1>Start a list</h1><ul><li>Item 1</li><li>Item 2</li></ul><p>End a list</p>";

        assertEquals(expected, markdownParser.parse(input));
    }

    @Test
    public void withH3HeaderLevel() {
        String input = "### This will be an h3";
        String expected = "<h3>This will be an h3</h3>";

        assertEquals(expected, markdownParser.parse(input));
    }

    @Test
    public void withH4HeaderLevel() {
        String input = "#### This will be an h4";
        String expected = "<h4>This will be an h4</h4>";

        assertEquals(expected, markdownParser.parse(input));
    }

    @Test
    public void withH5HeaderLevel() {
        String input = "##### This will be an h5";
        String expected = "<h5>This will be an h5</h5>";

        assertEquals(expected, markdownParser.parse(input));
    }

    @Test
    public void withParagraphAndBold() {
        String input = "This will be an paragraph with __bold__ text";
        String expected = "<p>This will be an paragraph with <strong>bold</strong> text</p>";

        assertEquals(expected, markdownParser.parse(input));
    }

    @Test
    public void withUnorderedListAndBoldText() {
        String input = "* Not bold item 1\n* __Bold__ item 1";
        String expected = "<ul><li>Not bold item 1</li><li><strong>Bold</strong> item 1</li></ul>";

        assertEquals(expected, markdownParser.parse(input));
    }

    @Test
    public void withUnorderedListAndItalicText() {
        String input = "* Not italic item 1\n* _Italic_ item 1";
        String expected = "<ul><li>Not italic item 1</li><li><em>Italic</em> item 1</li></ul>";

        assertEquals(expected, markdownParser.parse(input));
    }

    @Test
    public void withUnorderedListAndBoldAndItalicText() {
        String input = "* This is _italic_ and __bold__\n* This will not be italic and not bold";
        String expected = "<ul><li>This is <em>italic</em> and <strong>bold</strong></li><li>This will not be italic and not bold</li></ul>";
        
        assertEquals(expected, markdownParser.parse(input));
    }

    @Test
    public void multipleGrowingHeaders() {
        String input = "# This is h1\n## This is h2\n### This is h3\n#### This is h4";
        String expected = "<h1>This is h1</h1><h2>This is h2</h2><h3>This is h3</h3><h4>This is h4</h4>";

        assertEquals(expected, markdownParser.parse(input));
    }

    @Test
    public void everythingMixed() {
        String input = "This is a title\n# This is a h1\n## This is a h2\n### This is a h3\n#### This is a h4\n##### This" +
                " is a h5\n###### This is a h6\n This is a __bold__ text and an _italic_ text and here follows an\n" +
                "* unordered list\n* with some values";
        String expected = "<p>This is a title</p><h1>This is a h1</h1><h2>This is a h2</h2><h3>This is a h3</h3><h4>This is a h4" +
                "</h4><h5>This is a h5</h5><h6>This is a h6</h6><p> This is a <strong>bold</strong> text and an <em>italic</em> text" +
                " and here follows an</p><ul><li>unordered list</li><li>with some values</li></ul>";

        assertEquals(expected, markdownParser.parse(input));
    }
}