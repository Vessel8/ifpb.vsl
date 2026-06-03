package com.vslifb.prova3p3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestProva3P3 {

	public static void main( String[] args )
	{
		SpringApplication.from( Prova3P3::main )
				.with( TestcontainersConfiguration.class )
				.run( args );
	}
}
