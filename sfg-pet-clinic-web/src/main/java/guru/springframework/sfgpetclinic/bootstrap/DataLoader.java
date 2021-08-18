package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.*;
import guru.springframework.sfgpetclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;

@Component
public class DataLoader implements CommandLineRunner {

	private final OwnerService ownerService;
	private final VetService vetService;
	private final PetTypeService petTypeService;

	private final SpecialityService specialityService;

	private final VisitService visitService;
	
	
	DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialityService specialityService, VisitService visitService){
		this.ownerService = ownerService;
		this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
        this.visitService = visitService;
    }

	
	@Override
	public void run(String... args) throws Exception {
	    int count = petTypeService.findAll().size();
	    if(count == 0){
            loadData();

        }

    }

    private void loadData() {
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType saveDogPetType = petTypeService.save(dog);

        PetType cat = new PetType();
        dog.setName("Cat");
        PetType saveCatPetType = petTypeService.save(cat);

        Speciality radiology = new Speciality();
        radiology.setDescription("radiology");
        Speciality savedRadiology = specialityService.save(radiology);

        Speciality surgery = new Speciality();
        surgery.setDescription("surgery");
        Speciality savedSurgery = specialityService.save(surgery);

        Speciality dentistry = new Speciality();
        dentistry.setDescription("dentist");
        Speciality savedDentistry = specialityService.save(dentistry);

        Owner owner1 = new Owner();
        owner1.setFirstName("Michael");
        owner1.setLastName("Weston");
        owner1.setAddress("engelsa 126");
        owner1.setCity("SPB");
        owner1.setTelephone("123123123");

        Pet mikesDog = new Pet();
        mikesDog.setOwner(owner1);
        mikesDog.setPetType(saveDogPetType);
        mikesDog.setName("Porter");
        mikesDog.setBirthDate(LocalDate.now());

        owner1.getPets().add(mikesDog);
        ownerService.save(owner1);


        Owner owner2 = Owner.builder().firstName("Fiona")
                .lastName("Glenanne")
                .address("Ullantorpantie 126")
                .city("Espoo")
                .pets(new HashSet<>())
                .telephone("66666")
                        .build();
//        Owner owner2 = new Owner();
//        owner2.setFirstName("Fiona");
//        owner2.setLastName("Glenanne");
//        owner2.setAddress("Ullantorpantie 126");
//        owner2.setCity("Espoo");
//        owner2.setTelephone("66666");



        Pet fionaCat = new Pet();
        fionaCat.setOwner(owner2);
        fionaCat.setPetType(saveCatPetType);
        fionaCat.setName("Myska");
        fionaCat.setName("Myska");
        fionaCat.setBirthDate(LocalDate.now());

        owner2.getPets().add(fionaCat);


        ownerService.save(owner2);

        Visit visit = new Visit();
        visit.setPet(fionaCat);
        visit.setDate(LocalDate.now());
        visit.setDescription("Visit description");
        visitService.save(visit);

        System.out.println("Loaded Owners....");

        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");
        vet1.getSpecialities().add(savedSurgery);

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");
        vet2.getSpecialities().add(savedRadiology);
        vetService.save(vet2);

        System.out.println("Loaded Vets....");
    }

}
