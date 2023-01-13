/**
 * 
 */
package mathaid;

import static java.lang.Integer.parseInt;
import static java.lang.Runtime.getRuntime;
import static java.lang.System.getProperty;
import static java.lang.System.getenv;
import static java.nio.file.Paths.get;

/*
 * Date: 8 May 2020----------------------------------------------------------- 
 * Time created: 17:11:53---------------------------------------------------  
 * Package: mathaid.calculator.base------------------------------------------------ 
 * Project: LatestPoject2------------------------------------------------ 
 * File: Device.java------------------------------------------------------ 
 * Class name: Device------------------------------------------------ 
 */
/**
 * The {@code Device} class contains a set of useful static methods when writing
 * device-specific programmes
 * 
 * @author Oruovo Anthony Etineakpopha
 * 
 */
public final class Device {

	/*
	 * Date: 8 May 2020-----------------------------------------------------------
	 * Time created: 17:11:53---------------------------------------------------
	 */
	/**
	 * Should prevent a new instance
	 */
	private Device() {
	}

	/*
	 * Date: 9 May 2020-----------------------------------------------------------
	 * Time created: 05:56:49--------------------------------------------
	 */
	/**
	 * Returns the number of processors in a device. This is useful for concurrent,
	 * multi-threaded, distributive and parallel computing.
	 * 
	 * @return the number of processors in a device
	 */
	public static final int numOfProcessors() {
		return parseInt(getenv("NUMBER_OF_PROCESSORS"));
	}

	/*
	 * Date: 22 Jul 2021-----------------------------------------------------------
	 * Time created: 12:12:19--------------------------------------------
	 */
	/**
	 * Gets the system-dependent line separator. This value is always the same each
	 * time this method is called.
	 * 
	 * @return a {@code String} object that represents the symbolic line separator
	 *         of the current device
	 */
	public static final String lineSeparator() {
		return getenv("line.separator");
	}

	/*
	 * Date: 19 Jul 2021-----------------------------------------------------------
	 * Time created: 13:24:41--------------------------------------------
	 */
	/**
	 * <p>
	 * Gets the system-dependent line separator. This value is always the same each
	 * time this method is called.
	 * </p>
	 * <p>
	 * Note that if you are going to be using this in regex method arguments, it
	 * should be called twice. Please see the implementation of
	 * {@link #getFullPathForIncluding(Class)} for details
	 * </p>
	 * 
	 * @return a {@code String} object that represents the symbolic file separator
	 *         of the current device
	 */
	public static final String fileSeparator() {
		return getProperty("file.separator");
	}

	/*
	 * Date: 22 Jul 2021-----------------------------------------------------------
	 * Time created: 12:17:44--------------------------------------------
	 */
	/**
	 * Gets whether <code> {@link #numOfProcessors()} &gt; 1</code>
	 * 
	 * @return {@code true} if this device has more than one processor or else
	 *         returns {@code false}
	 * 
	 */
	public static final boolean hasMultiProcessor() {
		return numOfProcessors() > 1;
	}

	/*
	 * Date: 22 Jul 2021-----------------------------------------------------------
	 * Time created: 12:21:36--------------------------------------------
	 */
	/**
	 * Gets the max number of processors available to the JVM. This value may change
	 * multiple times during a single runtime.
	 * 
	 * @return the max number of processors available to the JVM
	 */
	public static final int availableProcessors() {
		return getRuntime().availableProcessors();
	}

	/*
	 * Date: 22 Jul 2021-----------------------------------------------------------
	 * Time created: 12:24:53--------------------------------------------
	 */
	/**
	 * Returns this project's path as a {@code String}
	 * 
	 * @return a {@code String} specifying this project folder's path
	 */
	public static final String getProjectPath() {
		return get("").toAbsolutePath().toString();
	}

	/*
	 * Date: 19 Jul 2021-----------------------------------------------------------
	 * Time created: 13:58:07--------------------------------------------
	 */
	/**
	 * Gets the absolute path from the storage name to the folder that contains the
	 * argument file (.java file) including the argument file itself as a
	 * {@code String} representing the path.
	 * 
	 * @param klass the class whose folder's path and file is to be returned
	 * @return a {@code String} that represents the path of the containing folder
	 *         and file of the argument
	 */

	public static final String getFullPathForIncluding(Class<?> klass) {
		return get(Device.getProjectPath(), "src", klass.getName().replaceAll("\\.", "\\" + fileSeparator()))
				.toAbsolutePath().toString() + ".java";
	}

	/*
	 * Date: 19 Jul 2021-----------------------------------------------------------
	 * Time created: 13:58:07--------------------------------------------
	 */
	/**
	 * Gets the absolute path from the storage name to the folder that contains the
	 * argument file (.class file) including the argument file itself as a
	 * {@code String} representing the path.
	 * 
	 * @param klass the class whose folder's path and file is to be returned
	 * @return a {@code String} that represents the path of the containing folder
	 *         and file of the argument
	 */
	public static final String getFullPathForClassFileIncluding(Class<?> klass) {
		return get(Device.getProjectPath(), "bin", klass.getName().replaceAll("\\.", "\\" + fileSeparator()))
				.toAbsolutePath().toString() + ".class";
	}

	/*
	 * Date: 19 Jul 2021-----------------------------------------------------------
	 * Time created: 13:58:07--------------------------------------------
	 */
	/**
	 * Gets the absolute path from the storage name to the folder that contains the
	 * argument file (.java file) but excludes the argument file itself as a
	 * {@code String} representing the path.
	 * 
	 * @param klass the class whose folder's path is to be returned
	 * @return a {@code String} that represents the path of the containing folder of
	 *         the argument
	 */
	public static final String getFullPathForClassFileExcluding(Class<?> klass) {
		return getFullPathForClassFileIncluding(klass).replace(klass.getSimpleName() + ".class", "");

	}

	/*
	 * Date: 19 Jul 2021-----------------------------------------------------------
	 * Time created: 13:58:07--------------------------------------------
	 */
	/**
	 * Gets the absolute path from the storage name to the folder that contains the
	 * argument file (.class file) but excludes the argument file itself as a
	 * {@code String} representing the path.
	 * 
	 * @param klass the class whose folder's path is to be returned
	 * @return a {@code String} that represents the path of the containing folder of
	 *         the argument
	 */
	public static final String getFullPathForExcluding(Class<?> klass) {
		return getFullPathForIncluding(klass).replace(klass.getSimpleName() + ".java", "");

	}
}
