package Project40.gladiolen_backend.services;

import Project40.gladiolen_backend.models.Union;
import Project40.gladiolen_backend.repositories.UnionRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnionService {

    private final UnionRepository unionRepository;

    @PostConstruct
    public void init() {
        if (unionRepository.findAll().isEmpty()) {
            Union union = Union.builder()
                    .name("Union1")
                    .address("Union Address")
                    .postalCode(12345)
                    .municipality("Union Municipality")
                    .vatNumber("Union VAT Number")
                    .accountNumber("Union Account Number")
                    .numberOfParkingTickets(1)
                    .build();
            Union union1 = Union.builder()
                    .name("Union2")
                    .address("Union Address")
                    .postalCode(12345)
                    .municipality("Union Municipality")
                    .vatNumber("Union VAT Number")
                    .accountNumber("Union Account Number")
                    .numberOfParkingTickets(1)
                    .build();
            unionRepository.save(union);
            unionRepository.save(union1);
        }
    }

    @Transactional
    public void createUnion(Union union) {
        Union newUnion = Union.builder()
                .name(union.getName())
                .address(union.getAddress())
                .postalCode(union.getPostalCode())
                .municipality(union.getMunicipality())
                .vatNumber(union.getVatNumber())
                .accountNumber(union.getAccountNumber())
                .numberOfParkingTickets(union.getNumberOfParkingTickets())
                .build();
        unionRepository.save(newUnion);
    }

    public Union getUnionById(Long id) {
        return unionRepository.findById(id).orElseThrow(() -> new RuntimeException("Union not found"));
    }

    public List<Union> getAllUnions() {
        return unionRepository.findAll();
    }

    @Transactional
    public void updateUnion(Long id, Union union) {
        Union existingUnion = unionRepository.findById(id).orElseThrow(() -> new RuntimeException("Union not found"));
        existingUnion.setName(union.getName());
        existingUnion.setAddress(union.getAddress());
        existingUnion.setPostalCode(union.getPostalCode());
        existingUnion.setMunicipality(union.getMunicipality());
        existingUnion.setVatNumber(union.getVatNumber());
        existingUnion.setAccountNumber(union.getAccountNumber());
        existingUnion.setNumberOfParkingTickets(union.getNumberOfParkingTickets());
        unionRepository.save(existingUnion);
    }

    public void deleteUnion(Long id) {
        unionRepository.deleteById(id);
    }
}