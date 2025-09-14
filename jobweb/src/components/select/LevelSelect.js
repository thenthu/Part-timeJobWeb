import React from "react";
import AsyncSelect from "react-select/async";
import Apis, { endpoints } from "../../configs/Apis";

const LevelSelect = ({ value, onChange }) => {
  const loadOptions = async (inputValue) => {
    try {
      const res = await Apis.get(`${endpoints.levels}?kw=${inputValue}`);
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
      placeholder="Chọn trình độ..."
      value={value}
      getOptionLabel={(e) => e.levelName}
      getOptionValue={(e) => e.levelId}
      loadOptions={loadOptions}
      onChange={selected => onChange(selected || null)}
    />
  );
};

export default LevelSelect;
