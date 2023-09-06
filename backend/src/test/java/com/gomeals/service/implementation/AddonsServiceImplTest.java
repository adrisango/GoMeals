package com.gomeals.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.gomeals.model.Addons;
import com.gomeals.repository.AddonsRepository;

@SpringBootTest
@Transactional
public class AddonsServiceImplTest {

        @InjectMocks
        private AddonsServiceImpl addonsServiceImpl;

        @Mock
        AddonsRepository addonsRepository;

        @Test
        public void testGetAddon() {
                Addons addon = new Addons(1, "Cheese", 6.45f, 1);
                Mockito.when(addonsRepository.findById(1)).thenReturn(
                                Optional.of(addon));

                Addons retrievedAddon = addonsServiceImpl.getAddon(addon.getAddonId());
                assertNotNull(retrievedAddon);
        }

        @Test
        public void testCreateAddon() {
                Addons addon = new Addons(1, "Butter", 6.45f, 3);
                Mockito.when(addonsRepository.save(addon)).thenReturn(addon);
                String result = addonsServiceImpl.createAddon(addon);

                assertEquals("Addon added successfully", result);
        }

        @Test
        public void testUpdateAddon() {
                Addons oldAddOn = new Addons(1, "Avocado", 6.45f,
                                3);
                Addons newAddOn = new Addons(1, "Mashed Avocado", 3.25f,
                                3);

                Mockito.when(addonsRepository.findById(1)).thenReturn(Optional.of(oldAddOn));
                Mockito.when(addonsRepository.save(oldAddOn)).thenReturn(oldAddOn);

                String updateStatus = addonsServiceImpl.updateAddon(newAddOn);
                assertEquals("Addon Item updated successfully.", updateStatus);

        }

        @Test
        public void testDeleteAddon() {
                int id = 1;
                doNothing().when(addonsRepository).deleteById(1);
                String result = addonsServiceImpl.deleteAddon(id);
                assertEquals("Addon deleted successully", result);
        }

        @Test
        void testGetAllSupplierAddonsWithValidSupplierId() {

                Integer supplierId = 2;
                List<Addons> expectedSupplierAddons = new ArrayList<>();
                expectedSupplierAddons
                                .add(new Addons(1, "Jalapenos", 6.45f, supplierId));
                expectedSupplierAddons
                                .add(new Addons(2, "Crunchy Onions", 3.25f, supplierId));
                expectedSupplierAddons
                                .add(new Addons(3, "Tandoori Naan", 4.56f, supplierId));
                Mockito.when(addonsRepository.findAllBySupplierId(supplierId)).thenReturn(expectedSupplierAddons);

                List<Addons> actualSupplierAddons = addonsServiceImpl.getAllSupplierAddons(supplierId);

                assertEquals(expectedSupplierAddons, actualSupplierAddons);
        }

        @Test
        void testGetAllSupplierAddonsWithInvalidSupplierId() {
                int supplierId = 1;
                List<Addons> expectedSupplierAddons = new ArrayList<>();
                Mockito.when(addonsRepository.findAllBySupplierId(supplierId)).thenReturn(expectedSupplierAddons);
                List<Addons> actualSupplierAddons = addonsServiceImpl.getAllSupplierAddons(supplierId);
                assertEquals(0, actualSupplierAddons.size());
        }

        @Test
        void testGetAllSupplierAddonsWithNullSupplierId() {
                int supplierId = 0;
                List<Addons> actualSupplierAddons = addonsServiceImpl.getAllSupplierAddons(supplierId);
                assertEquals(0, actualSupplierAddons.size());
        }
}
