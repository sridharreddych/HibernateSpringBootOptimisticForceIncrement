package com.bookstore;

import com.bookstore.service.BookstoreService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainApplication {

    private final BookstoreService bookstoreService;

    public MainApplication(BookstoreService bookstoreService) {
        this.bookstoreService = bookstoreService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {
            
            // running this application will
            // throw org.springframework.orm.ObjectOptimisticLockingFailureException
            
            bookstoreService.editChapter();
        };
    }
}

/*
 * How To Increment The Version Of The Locked Entity Even If This Entity Was Not Modified OPTIMISTIC_FORCE_INCREMENT

Description: This application is a sample of how OPTIMISTIC_FORCE_INCREMENT works in MySQL. This is useful when you want to increment the version of the locked entity even if this entity was not modified. Via OPTIMISTIC_FORCE_INCREMENT the version is updated (incremented) at the end of the currently running transaction.

Key points:

use a root entity, Chapter (which uses @Version)
several editors load a chapter and perform modifications mapped via the Modification entity
between Modification (child-side) and Chapter (parent-side) there is a lazy unidirectional @ManyToOne association
for each modification, Hibernate will trigger an INSERT statement against the modification table, therefore the chapter table will not be modified by editors
but, Chapter entity version is needed to ensure that modifications are applied sequentially (the author and editor are notified if a modificaton was added since the chapter copy was loaded)
the version is forcibly increased at each modification (this is materialized in an UPDATE triggered against the chapter table at the end of the currently running transaction)
set OPTIMISTIC_FORCE_INCREMENT in the corresponding repository
rely on two concurrent transactions to shape the scenario that will lead to an exception of type ObjectOptimisticLockingFailureException
 * 
 * 
 */
