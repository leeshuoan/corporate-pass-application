import * as React from "react";
import { useEffect, useState } from "react";
import { toast } from "react-toastify";
import { useParams } from "react-router-dom";
import {
  Grid,
  Typography,
  Box,
  TextField,
  MenuItem,
  Select,
  InputLabel,
  FormControl,
  Button,
} from "@mui/material";

const AddCorporatePassForm = ({
  fetchAllCorporatePass,
  handleCloseAddPass,
}) => {
  const params = useParams();
  const [type, setType] = useState(params.attractionId ?? 1);
  const [placeOfInterest, setPlaceOfInterest] = React.useState([
    { attractionId: 1, name: "Attraction 1" },
  ]);

  const handleTypeChange = (event) => {
    setType(event.target.value);
  };

  useEffect(() => {
    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/attractions/all`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        setPlaceOfInterest(data.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);

    if (data.get("corporatePassId").trim() == "") {
      toast.error("Please fill all the fields");
      return;
    }

    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/corporatePass`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        corporatePassId: data.get("corporatePassId"),
        status: "available",
        email: "efwfefwfw",
        attractionId: type,
      }),
    })
      .then((response) => response.json())
      .then((responseJson) => {
        if (responseJson.corporatePassId == data.get("corporatePassId")) {
          toast.success("Corporate Pass added successfully!");
          fetchAllCorporatePass();
          handleCloseAddPass();
          return;
        }
      })
      .catch((error) => {
        toast.error(error);
      });
  };

  return (
    <Box component="form" noValidate onSubmit={handleSubmit}>
      <Grid container direction="column">
        <Typography variant="h4" component="div" gutterBottom>
          New Corporate Pass
        </Typography>
        <br></br>
        <FormControl variant="standard" sx={{ m: 1, minWidth: 120 }}>
          <InputLabel id="poi">Places of Interest</InputLabel>
          <Select
            labelId="poi"
            id="placesOfInterest"
            value={type}
            onChange={handleTypeChange}
            label="Places of Interest">
            {placeOfInterest.map((place, index) => {
              return (
                <MenuItem key={index} value={place.attractionId}>
                  {place.name}
                </MenuItem>
              );
            })}
          </Select>
        </FormControl>
        <br></br>
        <TextField
          required
          fullWidth
          id="corporatePassId"
          label="Corporate Pass Card Number"
          name="corporatePassId"
          autoComplete="corporatePassId"
        />
        <br></br>

        <Button variant="contained" color="primary" type="submit">
          Add
        </Button>
      </Grid>
    </Box>
  );
};

export default AddCorporatePassForm;
