package com.system27.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
@EnableSwagger2
public class MongoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MongoApplication.class, args);
	}

	private static MongoDatabase md;

	public static MongoDatabase getDatabase(){
		if (md == null){
			MongoClient mc = new MongoClient("localhost", 59904);
			md = mc.getDatabase("test");
		}
		return md;
	}

	@Override
	public void run(String... args) throws Exception {
		MongoDatabase db = getDatabase();
		MongoCollection<Document> people = db.getCollection("people");
		Document person1 = new Document();
		person1.append("name", "bob");
		person1.append("age", 28);
		person1.append("salary", 2000);
		person1.append("likes", Arrays.asList("Chocolate","Soda"));

		Document person2 = new Document();
		person2.append("name", "bill");
		person2.append("age", 28);
		person2.append("salary", 5000);
		person2.append("likes", Arrays.asList("Chocolate","Candy"));

		Document person3 = new Document();
		person3.append("name", "John");
		person3.append("age", 27);
		person3.append("salary", 5000);
		person3.append("likes", Arrays.asList("Chocolate","Lemon"));

		Document person4 = new Document();
		person4.append("name", "Josh");
		person4.append("age", 28);
		person4.append("salary", 5000);
		person4.append("likes", Arrays.asList("Lemon","Candy"));

		Document person5 = new Document();
		person5.append("name", "Sarah");
		person5.append("age", 27);
		person5.append("salary", 6000);
		person5.append("likes", Arrays.asList("Sweets","ice cream"));

		Document person6 = new Document();
		person6.append("name", "Ilene");
		person6.append("age", 27);
		person6.append("salary", 7000);
		person6.append("likes", Arrays.asList("Strawberry","Banana"));

		Document person7 = new Document();
		person7.append("name", "Sabrina");
		person7.append("age", 26);
		person7.append("salary", 5000);
		person7.append("likes", Arrays.asList("Taco","Strawberry"));
		people.insertMany(Arrays.asList(person1,person2,person3,
				person5,person4,person6,person7));
	}
}
