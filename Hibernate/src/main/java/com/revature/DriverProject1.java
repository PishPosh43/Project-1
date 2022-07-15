package com.revature;

import java.sql.Connection;
import java.util.Scanner;

import com.revature.models.DummyOtherClass;
import com.revature.models.DummyUser;
import com.revature.util.Configuration;

public class DriverProject1 {

	public static void main(String[] args) {

		Configuration cfg = new Configuration();
		Configuration.setDbUrl(System.getenv("DB_URL"));
		Configuration.setDbUsername(System.getenv("DB_USERNAME"));
		Configuration.setDbPassword(System.getenv("DB_PWORD"));

		cfg.addAnnotatedClass(DummyUser.class);

		Connection con = cfg.getConnection();

		DummyOtherClass otherDummy = new DummyOtherClass(5, "testerinog");

		DummyUser dUser = new DummyUser(10, "dUser", "One");
		DummyUser dUser2 = new DummyUser(11, "dUser2", "Two");
		DummyUser dUser3 = new DummyUser(13, "dUser3", "Three");

		// cfg.insert(dUser2);

		Scanner scan = new Scanner(System.in);
		int i = 0;

		System.out.println("Please make one of the following selections: ");

		System.out.println("======================");
		System.out.println("Insert user dUser3: Press 1");
		System.out.println("Retrieve information about a dUser2: Press 2");
		System.out.println("Delete user: Press 3");
		System.out.println("Update user (Change dUser3's first name): Press 4");
		System.out.println("======================");
		System.out.println();

		while (i < 20) {
			if(i>0) {System.out.println("======================");
			System.out.println("Insert user dUser3: Press 1");
			System.out.println("Retrieve information about a dUser2: Press 2");
			System.out.println("Delete user: Press 3");
			System.out.println("Update user (Change dUser3's first name): Press 4");
			System.out.println("======================");
			System.out.println();
			}
			int s = scan.nextInt();
			if (s == 1) {
				cfg.insert(dUser3);
			} else if (s == 2) {
				cfg.read(dUser3);

			} else if (s == 3) {
				cfg.delete(dUser3);
			} else if (s == 4) {

				System.out.println("Enter a new first name: ");
				String changeFName = scan.next();
				dUser3.setFirstName(changeFName);
				cfg.update(dUser3);
			}
			i++;
		}
	}
}
