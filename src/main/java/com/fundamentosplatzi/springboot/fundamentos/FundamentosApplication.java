package com.fundamentosplatzi.springboot.fundamentos;

import com.fundamentosplatzi.springboot.fundamentos.bean.MyBean;
import com.fundamentosplatzi.springboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentosplatzi.springboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentosplatzi.springboot.fundamentos.component.ComponentDependency;
import com.fundamentosplatzi.springboot.fundamentos.entity.User;
import com.fundamentosplatzi.springboot.fundamentos.pojo.UserPojo;
import com.fundamentosplatzi.springboot.fundamentos.repository.UserRepository;
import com.fundamentosplatzi.springboot.fundamentos.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.convert.converter.ConvertingComparator;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FundamentosApplication implements CommandLineRunner {

	Log LOGGER = LogFactory.getLog(FundamentosApplication.class);

	//@Autowired
	private ComponentDependency componentDependency;
	private MyBean myBean;
	private MyBeanWithDependency myBeanWithDependency;
	private MyBeanWithProperties myBeanWithProperties;
    private UserPojo userPojo;
	private UserRepository userRepository;
	private UserService userService;

	public FundamentosApplication(
				@Qualifier("componentTwoImplement")  ComponentDependency componentDependency,
				MyBean myBean,
				MyBeanWithDependency myBeanWithDependency,
				MyBeanWithProperties myBeanWithProperties,
                UserPojo userPojo,
				UserRepository userRepository,
				UserService userService
    ) {
			this.componentDependency = componentDependency;
			this.myBean=myBean;
			this.myBeanWithDependency=myBeanWithDependency;
			this.myBeanWithProperties=myBeanWithProperties;
            this.userPojo=userPojo;
			this.userRepository=userRepository;
			this.userService=userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//this.ejemplosAnteriores();
		this.saveUsersInDatabase();
		//this.getInformationJpqlFromUser();
		//this.getInformationJpqlFromUser2();
		this.saveWhithErrorTransaccional();

	}

	private void saveWhithErrorTransaccional(){
		User user1=new User("Transaccional1","Transaccional1@gmail.com", LocalDate.of(2021,11,22));
		User user2=new User("Transaccional2","Transaccional2@gmail.com", LocalDate.of(2021,11,22));
		User user3=new User("Transaccional3","Transaccional1@gmail.com", LocalDate.of(2021,11,22));
		User user4=new User("Transaccional4","Transaccional4@gmail.com", LocalDate.of(2021,11,22));

		List<User> users= Arrays.asList(user1,user2,user3,user4);
		try {
			userService.saveTransaccional(users);
		}catch (Exception e){
			LOGGER.error("Error dentro del metodo transaccional"+e);
		}
		userService.getAllUsers()
				.stream()
				.forEach(user->LOGGER.info("Este es el usuario dentro del metodo transaccionl" + user));


	}

	private void getInformationJpqlFromUser2(){

		userRepository.findByBirthDateBetween(LocalDate.of(2021,3,1),LocalDate.of(2021,6,2))
				.stream()
				.forEach(user-> LOGGER.info("Usuario intervalo de fechas-->"+ user));

		userRepository.findByNameLikeOrderByIdDesc("%Jh%")
				.stream()
				.forEach(user-> LOGGER.info("usuario findByNameLikeOrderByIdDesc -->" + user.toString()));

		userRepository.findByNameContainingOrderByIdDesc("Jh")
				.stream()
				.forEach(user-> LOGGER.info("usuario findByNameContainingOrderByIdDesc -->" + user.toString()));

		LOGGER.info("Usuario Encontrado getAllByBirthDateAndEmail-->"
				+userRepository.getAllByBirthDateAndEmail(LocalDate.of(2021,3,20),"jmantillap1@gmail.com")
				.orElseThrow(()-> new RuntimeException("No se encontro el usuario a partir del parametro getAllByBirthDateAndEmail")));

	}



	private void getInformationJpqlFromUser(){
		LOGGER.info("Usurio consultado por email.-->"
				+userRepository.findByUserEmail("jmantillap@gmail.com").orElseThrow(()-> new RuntimeException("No se encontro el usuario")));

		userRepository.findAndSort("user", Sort.by("id").descending())
				.stream()
				.forEach(user -> LOGGER.info("User buscado y ordenado--> "+ user));

		userRepository.findAndSort("user", Sort.by("id").ascending())
				.stream()
				.forEach(user -> LOGGER.info("User buscado y ordenado--> "+ user));

		userRepository.findByName("Jhon")
				.stream()
				.forEach(user-> LOGGER.info("Usuario query method-->"+ user.toString()) );

		userRepository.findByEmailAndName("jmantillap@gmail.com","Jhon")
				.stream()
				.forEach(user-> LOGGER.info("Busqueda por email y nombre ->" + user.toString()));

	 	LOGGER.info("Usuario con query method findByEmailAndName-->"+userRepository.findByEmailAndName("jmantillap@gmail.com","Jhon")
				.orElseThrow(()-> new RuntimeException("Usuario no encontrado findByEmailAndName")));

		userRepository.findByNameLike("%J%")
				.stream()
				.forEach(user-> LOGGER.info("usuario findByNameLike -->" + user.toString()));

		userRepository.findByNameOrEmail(null,"user10@gmail.com")
				.stream()
				.forEach(user-> LOGGER.info("usuario findByNameOrEmail -->" + user.toString()));

		userRepository.findByNameOrEmail("user10",null)
				.stream()
				.forEach(user-> LOGGER.info("usuario findByNameOrEmail -->" + user.toString()));

	}

	private void saveUsersInDatabase(){
		User user1=new User("Jhon","jmantillap@gmail.com", LocalDate.of(2021,3,20));
		User user2=new User("Jhon","eliana@gmail.com", LocalDate.of(2021,5,10));
		User user3=new User("user3","user3@gmail.com", LocalDate.of(2021,6,10));
		User user4=new User("user4","user4@gmail.com", LocalDate.of(2021,7,10));
		User user5=new User("user5","user5@gmail.com", LocalDate.of(2021,8,10));
		User user6=new User("user6","user6@gmail.com", LocalDate.of(2021,9,10));
		User user7=new User("user7","user7@gmail.com", LocalDate.of(2021,10,10));
		User user8=new User("user8","user8@gmail.com", LocalDate.of(2021,11,10));
		User user9=new User("user9","user9@gmail.com", LocalDate.of(2021,12,10));
		User user10=new User("user10","user10@gmail.com", LocalDate.of(2021,7,20));
		User user11=new User("user11","user11@gmail.com", LocalDate.of(2021,6,01));
		User user12=new User("user12","user12@gmail.com", LocalDate.of(2021,1,03));

		List<User> list= Arrays.asList(user1,user2,user3,user4,user5,user6,user7,user8,user9,user10,user11,user12);
		list.stream().forEach(userRepository::save);


	}

	private void ejemplosAnteriores(){
		this.componentDependency.saludar();
		this.myBean.print();
		this.myBeanWithDependency.printWithDependency();
		System.out.println(this.myBeanWithProperties.function());
		System.out.println(userPojo.getEmail()+ " " + userPojo.getPassword() + " "+ userPojo.getAge());
		try {
			int value=10/0;
			LOGGER.debug("mi valor es->"+ value);
		}catch (Exception e){
			LOGGER.error("Esto es un errodel aplicativo->"+ e.getMessage());
		}
	}

}

