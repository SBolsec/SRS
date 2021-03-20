package me.sbolsec.pwdmgr;

/**
 * Simple password manager which provides methods
 * for creating a storage using a master password,
 * adding [address, password] pairs to the storage
 * and retrieving passwords by providing the address.
 */
public class Main {

    /**
     * Initializes storage for [address, password] pairs using
     * provided master password.
     * @param masterPassword master password used to initialize storage
     */
    public static void init(String masterPassword) {

    }

    /**
     * Puts [address, password] pair into the storage initialized by the
     * provided master password.
     * @param masterPassword master password
     * @param address address to add
     * @param password password to add
     */
    public static void put(String masterPassword, String address, String password) {

    }

    /**
     * Returns the password from the storage initialized by the provided
     * master password which belongs to the provided address.
     * @param masterPassword master password
     * @param address address for which to return password
     */
    public static void get(String masterPassword, String address) {

    }

    /**
     * Prints error message and usage instructions then terminates the program.
     */
    public static void printErrorMessageAndTerminate() {
        System.out.println("Invalid usage! Use one of the following commands:");
        System.out.println("\t1. init [masterPassword]");
        System.out.println("\t2. put [masterPassword] [address] [password]");
        System.out.println("\t3. get [masterPassword] [address]");
        System.exit(1);
    }

    /**
     * Starting point of program.
     * Calls required method based on command line arguments.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // check the args
        int len = args.length;
        if (len < 2 || len > 4) printErrorMessageAndTerminate();

        // call the required method
        switch (args[0]) {
            case "init":
                if (len != 2) printErrorMessageAndTerminate();
                init(args[1]);
                break;
            case "put":
                if (len != 4) printErrorMessageAndTerminate();
                put(args[1], args[2], args[3]);
                break;
            case "get":
                if (len != 3) printErrorMessageAndTerminate();
                get(args[1], args[2]);
                break;
            default:
                printErrorMessageAndTerminate();
        }
    }
}
