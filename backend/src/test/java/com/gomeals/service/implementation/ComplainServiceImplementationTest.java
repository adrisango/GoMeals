package com.gomeals.service.implementation;

import com.gomeals.model.Complain;
import com.gomeals.repository.ComplainRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@SpringBootTest
public class ComplainServiceImplementationTest {
        @Mock
        private ComplainRepository complainRepository;

        @InjectMocks
        private ComplainServiceImplementation complainServiceImplementation;

        @Test
        public void testCreateComplain() {
                long millis=System.currentTimeMillis();
                Date date = new Date(millis);

                Complain complain = new Complain(1,date,"pizza had no cheese","refunded meal",
                        "initiated",1,2,2);
                when(complainRepository.save(complain)).thenReturn(complain);

                Complain savedComplain = complainServiceImplementation.createComplain(complain);

                assertEquals(complain, savedComplain);
                verify(complainRepository, times(1)).save(complain);
        }

        @Test
        public void testGetComplainsByCustomerId() {
                long millis=System.currentTimeMillis();
                Date date = new Date(millis);

                List<Complain> complains = new ArrayList<>();

                Complain complain1 = new Complain(1,date,"pizza had no cheese","refunded meal",
                        "initiated",1,2,2);
                Complain complain2 = new Complain(2,date,"bad service","refunded meal",
                        "initiated",1,2,2);

                complains.add(complain1);
                complains.add(complain2);

                when(complainRepository.findComplainsByCustomerId(1)).thenReturn(complains);

                List<Complain> getComplains = complainServiceImplementation.getComplainsByCustomerId(1);

                assertEquals(complains, getComplains);
                verify(complainRepository, times(1)).findComplainsByCustomerId(1);
        }

        @Test
        public void testGetComplainsBySupplierId(){

                long millis=System.currentTimeMillis();
                Date date = new Date(millis);

                List<Complain> complains = new ArrayList<>();

                Complain complain1 = new Complain(1,date,"pizza had no cheese","refunded meal",
                        "initiated",1,3,2);
                Complain complain2 = new Complain(2,date,"bad service","refunded meal",
                        "initiated",1,3,2);

                complains.add(complain1);
                complains.add(complain2);

                when(complainRepository.findComplainsBySupplierId(3)).thenReturn(complains);

                List<Complain> getComplains = complainServiceImplementation.getComplainsBySupplierId(3);

                assertEquals(complains, getComplains);
                verify(complainRepository, times(1)).findComplainsBySupplierId(3);

        }

        @Test
        public void testGetComplainsByCustomerIdAndSupplierId() {

                Integer customerId = 1;
                Integer supplierId = 2;
                List<Complain> expectedComplains = Arrays.asList(new Complain(), new Complain());

                when(complainRepository.findComplainsByCustomerIdAndSupplierId(customerId, supplierId))
                        .thenReturn(expectedComplains);

                List<Complain> getComplains = complainServiceImplementation
                        .getComplainsByCustomerIdAndSupplierId(customerId, supplierId);

                assertEquals(expectedComplains, getComplains);
                verify(complainRepository, times(1))
                        .findComplainsByCustomerIdAndSupplierId(customerId, supplierId);
        }

        @Test
        void getAllComplains() {
                // when the service calls get all complains then it should return a list of complains
                long millis=System.currentTimeMillis();
                Date date = new Date(millis);

                when(complainRepository.findAll()).thenReturn(Arrays.asList(
                        new Complain(1,date,"pizza had no cheese","refunded meal",
                                "initiated",1,2,2),
                        new Complain(2,date,"missing 1 taco","refunded meal",
                                "initiated",1,2,2)
                ));
                List<Complain> result = complainServiceImplementation.getAllComplains();

                assertEquals(2,result.get(0).getDeliveryId());
                assertEquals(2,result.get(1).getComplainId());
        }

        @Test
        void getComplainById() {
                long millis=System.currentTimeMillis();
                Date date = new Date(millis);

                when(complainRepository.findById(1)).thenReturn(
                        Optional.of(new Complain(1,date,"pizza had no cheese","refunded meal",
                                "initiated",1,2,2))
                );
                Complain complain = complainServiceImplementation.getComplainById(1);

                assertEquals(2,complain.getSupplierId());
        }

        @Test
        void deleteComplain() {
                long millis=System.currentTimeMillis();
                Date date = new Date(millis);
                Complain complain = new Complain(1,date,"pizza had no cheese","refunded meal",
                        "initiated",1,2,2);

                when(complainRepository.findById(1)).thenReturn(Optional.of(complain));
                complainServiceImplementation.deleteComplain(complain.getComplainId());
                verify(complainRepository).deleteById(complain.getComplainId());
        }

        @Test
        void updateComplain() {
                long millis=System.currentTimeMillis();
                Date date = new Date(millis);

                Complain complain = new Complain(1,date,"pizza had no cheese","refunded meal",
                        "initiated",1,2,2);
                Complain newComplain = new Complain(1,date,"pizza had no cheese","refunded meal",
                        "closed",1,2,2);

                when(complainRepository.findById(1)).thenReturn(Optional.of(complain));
                when(complainRepository.save(complain)).thenReturn(newComplain);

                Complain updatedComplain = complainServiceImplementation.updateComplain(complain);

                assertEquals("closed",updatedComplain.getStatus());

        }

}

