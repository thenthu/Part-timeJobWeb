import React from "react";
import AsyncSelect from "react-select/async";
import Apis, { endpoints } from "../../configs/Apis";

const MajorSelect = ({ value, onChange }) => {
  const loadOptions = async (inputValue) => {
    try {
      const res = await Apis.get(`${endpoints.majors}?kw=${inputValue}`);
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
      placeholder="Chọn chuyên ngành..."
      value={value}
      getOptionLabel={(e) => e.majorName}
      getOptionValue={(e) => e.majorId}
      loadOptions={loadOptions}
      onChange={selected => onChange(selected || null)}
    />
  );
};

export default MajorSelect;
