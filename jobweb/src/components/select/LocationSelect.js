import React from "react";
import AsyncSelect from "react-select/async";
import Apis, { endpoints } from "../../configs/Apis";

const LocationSelect = ({ value,onChange }) => {
  const loadOptions = async (inputValue) => {
    try {
      const res = await Apis.get(`${endpoints.locations}?kw=${inputValue}`);
      return res.data;
    } catch (err) {
      console.error(err);
      return [];
    }
  };

  return (
    <AsyncSelect
      cacheOptions
      defaultOptions
      isClearable
      placeholder="Chọn địa điểm..."
      value={value}
      getOptionLabel={(e) => e.locationName}
      getOptionValue={(e) => e.locationId}
      loadOptions={loadOptions}
      onChange={selected => onChange(selected || null)}
    />
  );
};

export default LocationSelect;
