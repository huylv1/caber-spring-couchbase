/**
 * 
 */
package com.itsol.couchbase.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import com.couchbase.client.java.view.AsyncViewResult;
import com.couchbase.client.java.view.AsyncViewRow;
import com.couchbase.client.java.view.Stale;
import com.couchbase.client.java.view.ViewQuery;
import com.itsol.couchbase.service.exception.DataNotExistException;
import com.itsol.springmvc.model.Person;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * @author huylv
 *
 */
@Service("personService")
public class PersonServiceImpl implements PersonService {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(PersonServiceImpl.class);
	
	@Autowired
	private Bucket bucket;
	
	@Value("${couchbase.bucket}")
	private String bucketName;

	/* (non-Javadoc)
	 * @see com.itsol.springmvc.service.PersonService#findById(String)
	 */
	@Override
	public Person findById(String id) {
		
		JsonDocument doc = bucket.get("user::" + id);
		
		if (doc == null) {
			return null;
		}
		
		JsonObject object = doc.content();
		return createPersonFromJson(object);
	}

	/* (non-Javadoc)
	 * @see com.itsol.springmvc.service.PersonService#findByName(java.lang.String)
	 */
	@Override
	public Person findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.itsol.springmvc.service.PersonService#savePerson(com.itsol.springmvc.model.Person)
	 */
	@Override
	public void savePerson(Person person) {
		if (isPersonExist(person)) {
			throw new DataNotExistException("A person with id " + person.getId() + " already exist");
		}
		JsonObject data = JsonObject.create()
			    .put("id", person.getId())
			    .put("firstName", person.getFirstName())
			    .put("lastName", person.getLastName())
			    .put("type", person.getType())
			    .put("price", person.getPrice())
			    .put("birthday", person.getBirthday().getTime())
			    .put("gender", person.getGender())
			    .put("email", person.getEmail())
			    .put("rate", person.getRate())
			    .put("imageUrl", person.getImageUrl())
			    .put("password", BCrypt.hashpw(person.getPassword(), BCrypt.gensalt()));
		JsonDocument doc = JsonDocument.create("user::" + person.getId(), data);
		bucket.insert(doc);
	}

	/* (non-Javadoc)
	 * @see com.itsol.springmvc.service.PersonService#updatePerson(com.itsol.springmvc.model.Person)
	 */
	@Override
	public void updatePerson(Person person) {
		if (!isPersonExist(person)) {
			throw new DataNotExistException("A person with id (" + person.getId() + ") is not exist");
		}
		JsonDocument doc = bucket.get("user::" + person.getId());
		doc.content()
			.put("firstName", person.getFirstName())
			.put("lastName", person.getLastName())
			.put("gender", person.getGender())
			.put("birthday", person.getBirthday().getTime())
			.put("price", person.getPrice())
			.put("rate", person.getRate());
		bucket.replace(doc);
	}

	/* (non-Javadoc)
	 * @see com.itsol.springmvc.service.PersonService#deletePersonById(String)
	 */
	@Override
	public void deletePersonById(String id) {
		bucket.remove("user::" + id);
	}

	/* (non-Javadoc)
	 * @see com.itsol.springmvc.service.PersonService#findAllPersons()
	 */
	@Override
	public List<Person> findAllPersons() {
		List<AsyncViewRow> rows = getView(bucket, "caber", "users");
		
		List<Person> persons = new ArrayList<Person>();
		for (AsyncViewRow viewRow : rows) {
			JsonObject rawPerson = (JsonObject) viewRow.value();
			persons.add(createPersonFromJson(rawPerson));
		}
		return persons;
	}

	/**
	 * @param rawPerson
	 * @return
	 */
	private Person createPersonFromJson(JsonObject rawPerson) {
		return new Person(
					rawPerson.getString("id"),
					rawPerson.getString("firstName"),
					rawPerson.getString("lastName"),
					rawPerson.getString("password"),
					rawPerson.getString("type"),
					rawPerson.getDouble("price"),
					new Date(rawPerson.getLong("birthday")),
					rawPerson.getString("gender"),
					rawPerson.getString("email"),
					rawPerson.getInt("rate"),
					rawPerson.getString("imageUrl")
				);
	}

	/* (non-Javadoc)
	 * @see com.itsol.springmvc.service.PersonService#deleteAllPersons()
	 */
	@Override
	public void deleteAllPersons() {
		
	}

	/* (non-Javadoc)
	 * @see com.itsol.springmvc.service.PersonService#isPersonExist(com.itsol.springmvc.model.Person)
	 */
	@Override
	public boolean isPersonExist(Person person) {
		return bucket.exists("user::" + person.getId());
	}

	@Override
	public boolean login(String username, String password) {
		Person p = findById(username);
		if (p != null && BCrypt.checkpw(password, p.getPassword())) {
			return true;
		}
		return false;
	}
	
	/**
     * Extract a N1Ql result or throw if there is an issue.
     */
    private static List<JsonObject> extractResultOrThrow(N1qlQueryResult result) {
        if (!result.finalSuccess()) {
            LOGGER.warn("Query returned with errors: " + result.errors());
            throw new DataRetrievalFailureException("Query error: " + result.errors());
        }

        List<JsonObject> content = new ArrayList<JsonObject>();
        for (N1qlQueryRow row : result) {
            content.add(row.value());
        }
        
        return content;
    }

    /**
     * Helper method to log the executing query.
     */
    private static void logQuery(String query) {
        LOGGER.info("Executing Query: {}", query);
    }
    
    private ArrayList<AsyncViewRow> getView(final Bucket bucket, String designDoc, String view) {
		final ArrayList<AsyncViewRow> result = new ArrayList<AsyncViewRow>();
		final CountDownLatch latch = new CountDownLatch(1);
		System.out.println("METHOD START");

		bucket.async().query(
			ViewQuery.from(designDoc, view).limit(20).stale(Stale.FALSE))
			.doOnNext(new Action1<AsyncViewResult>() {
				@Override
				public void call(AsyncViewResult viewResult) {
					if (!viewResult.success()) {
						LOGGER.error(viewResult.error().toString());
					} else {
						LOGGER.info("Query is running!");
					}
				}
			}).flatMap(new Func1<AsyncViewResult, Observable<AsyncViewRow>>() {
				@Override
				public Observable<AsyncViewRow> call(AsyncViewResult viewResult) {
					return viewResult.rows();
				}
			}).subscribe(new Subscriber<AsyncViewRow>() {
				@Override
				public void onCompleted() {
					latch.countDown();
				}
				@Override
				public void onError(Throwable throwable) {
					LOGGER.error("Whoops: " + throwable.getMessage());
				}
				@Override
				public void onNext(AsyncViewRow viewRow) {
					result.add(viewRow);
				}
			});
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}
}
