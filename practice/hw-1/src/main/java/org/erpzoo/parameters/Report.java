package org.erpzoo.parameters;

/**
 * Report.
 *
 * @param title Title of report.
 *
 * @param content Content of report.
 */
public record Report(String title, String content) {
    @Override
    public String toString() {
        return String.format("%s\n\n%s", title, content);
    }
}
