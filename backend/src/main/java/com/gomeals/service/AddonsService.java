package com.gomeals.service;

import com.gomeals.model.Addons;

import java.util.List;

public interface AddonsService {

    Addons getAddon(int addonId);

    String createAddon(Addons addon);

    String updateAddon(Addons addon);

    String deleteAddon(int addonId);

    List<Addons> getAllSupplierAddons(int supplierId);
}
