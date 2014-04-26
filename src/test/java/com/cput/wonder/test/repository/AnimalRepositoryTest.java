package com.cput.wonder.test.repository;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.cput.my.wonder.app.config.ConnectionConfig;
import com.cput.my.wonder.domain.Animal;
import com.cput.my.wonder.domain.AnimalStatus;
import com.cput.my.wonder.domain.Habitat;
import com.cput.my.wonder.repository.AnimalRepository;
import java.sql.Date;
import java.util.Calendar;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Khanya
 */
public class AnimalRepositoryTest {

    public static ApplicationContext ctx;
    private Long id;
    private AnimalRepository repo;

    public AnimalRepositoryTest() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
//    @Test
//     public void hello() {}
    
    @Test
    public void createAnimal() {
        
        repo = ctx.getBean(AnimalRepository.class);
        Habitat habitat = new Habitat.Builder("104").block("D").build();
        AnimalStatus status = new AnimalStatus.Builder("Good stuff").date("12-04-14").build();
        Animal animal = new Animal.Builder(12).Name("Nonjana").Color("black").habitat(habitat).Species("Dog").status(status).adopted(false).build();
        repo.save(animal);
        id = animal.getId();
        Assert.assertNotNull(animal);
        
    }
    
    @Test   (dependsOnMethods = "createPerson") 
    public void readAnimal()
    {
        repo = ctx.getBean(AnimalRepository.class);
        Animal animal = repo.findOne(id);        
        Assert.assertEquals(animal.getName(), "Nonjana");
    }
    
    @Test(dependsOnMethods = "updateAnimal")
    public void updateAnimal()
    {
        repo = ctx.getBean(AnimalRepository.class);
        Animal animal = repo.findOne(id);
        
        Animal updateAnimal = new Animal.Builder(13).animal(animal).Name("Darling").build();
        repo.save(updateAnimal);
        Animal updatedAnimal = repo.findOne(id);
        Assert.assertEquals(updatedAnimal.getName(), "Darling");    
        
    }    
    @Test(dependsOnMethods = "deleteAnimal")
    public void deleteAnimal()
    {
        repo = ctx.getBean(AnimalRepository.class);
        Animal animal = repo.findOne(id);
        
        repo.delete(animal);        
        Animal deletedAnimal = repo.findOne(id);        
        Assert.assertNull(deletedAnimal);        
    }    
    @BeforeClass
    public static void setUpClass() throws Exception {
        ctx = new AnnotationConfigApplicationContext(ConnectionConfig.class);
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
        
    }
}
