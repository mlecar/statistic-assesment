### Author
	Name: Marcelo Lecar de Carvalho
	email: marcelo.lecar@gmail.com

## Environment
	Maven: 
		Apache Maven 3.5.0 (ff8f5e7444045639af65f6095c62210b5713f426; 2017-04-03T16:39:06-03:00)
	
	Java: 
		java version "1.8.0_144"
		Java(TM) SE Runtime Environment (build 1.8.0_144-b01)
		Java HotSpot(TM) 64-Bit Server VM (build 25.144-b01, mixed mode)
		
	IDE:
		Spring Tool Suite 3.9.1.RELEASE

## How to build

    $ mvn clean install

## How to execute

		$ java -Xmx1024m -jar statistic-assessment-1.0.0-SNAPSHOT.jar
    
## How to test/use

	curl:
		Transactions:
			$ curl -v -XPOST -d '{"amount": 5.0, "timestamp":1513473979605}' http://localhost:8080/transactions -H "Content-type: application/json"
		Statistics:
			$ curl -v -XGET http://localhost:8080/statistics

## Code Coverage
	After command execution "mvn clean install", there will be inside target folder code coverage data.
		target/site/jacoco/index.html

## Jmeter Test
	A jmeter test was added in src/main/load/smoke-test.jmx, just to be sure that concurrency was not generating issues

## VisualVM 1.4
	Used VisualVM to validate application behaviour, creating classes, managing threads
		

