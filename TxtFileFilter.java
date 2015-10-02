package TestOne;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class TxtFileFilter extends FileFilter {

	@Override
	public boolean accept(File file) {
		if (file.isDirectory()) {
			return false;
		}
		String s = file.getName();
		return s.endsWith(".txt");
	}

	@Override
	public String getDescription() {

		return "*.txt";
	}

}
