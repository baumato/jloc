package de.baumato.loc;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import org.kohsuke.args4j.Localizable;

/**
 *
 * @author baumato
 *
 */
public enum Messages implements Localizable {
	DIR_DOES_NOT_EXIST;

	@Override
	public String formatWithLocale(Locale locale, Object... args) {
		ResourceBundle localized = ResourceBundle.getBundle("messages", locale);
		return MessageFormat.format(localized.getString(name()), args);
	}

	@Override
	public String format(Object... args) {
		return formatWithLocale(Locale.getDefault(), args);
	}

	public static ResourceBundle getResourceBundle() {
		return ResourceBundle.getBundle("messages");
	}
}
