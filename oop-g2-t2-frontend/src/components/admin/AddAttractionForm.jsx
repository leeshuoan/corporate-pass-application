import * as React from "react";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { toast } from "react-toastify";
import {
  Typography,
  Box,
  TextField,
  Grid,
  Button,
  Radio,
  RadioGroup,
  FormControl,
  FormLabel,
  FormControlLabel,
} from "@mui/material";

const AddAttractionForm = ({
  fetchAllAttraction,
  handleCloseAddAttraction,
}) => {
  const params = useParams();
  const [passType, setPassType] = useState("electronic");

  const handlePassTypeChange = (event) => {
    setPassType(event.target.value);
  };

  const handleSubmit = (event) => {
    console.log("submitted");
    event.preventDefault();
    const data = new FormData(event.currentTarget);

    const regex = /^-?\d+\.?\d*$/;
    if (
      data.get("name") == "" ||
      data.get("address") == "" ||
      data.get("replacementFee") == "" ||
      data.get("cardName") == "" ||
      data.get("cardType") == ""
    ) {
      toast.error("Please fill all the fields");
      return;
    }
    if (!regex.test(data.get("replacementFee"))) {
      toast.error("Invalid replacement fee!");
      return;
    }

    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/attractions/add`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        name: data.get("name"),
        address: data.get("address"),
        replacementFee: data.get("replacementFee"),
        cardName: data.get("cardName"),
        cardType: data.get("cardType"),
        passType: passType,
      }),
    })
      .then((response) => response.json())
      .then((responseJson) => {
        console.log(responseJson);
        if (responseJson.status == 400) {
          toast.error(responseJson.message);
        } else {
          handleCloseAddAttraction();
          toast.success("New attraction added successfully!");
        }
      })
      .catch((error) => {
        console.log(error);
        toast.error(errorMessage);
      });
  };

  return (
    <Box component="form" noValidate onSubmit={handleSubmit}>
      <Grid container direction="column">
        <Typography variant="h4" component="div" gutterBottom>
          New Attraction
        </Typography>
        {/* name */}
        <br></br>
        <TextField
          required
          fullWidth
          id="name"
          label="Name"
          name="name"
          autoComplete="name"
        />
        <br></br>
        {/* address */}
        <TextField
          required
          multiline
          rows={4}
          fullWidth
          id="address"
          label="Address"
          name="address"
          autoComplete="address"
        />
        {/* replacement fee */}
        <br></br>
        <TextField
          required
          fullWidth
          id="replacementFee"
          label="Replacement Fee"
          name="replacementFee"
          autoComplete="replacementFee"
        />
        <br></br>
        {/* cardname */}
        <TextField
          required
          fullWidth
          id="cardName"
          label="Card Name"
          name="cardName"
          autoComplete="cardName"
        />
        {/* card type */}
        <br></br>
        <TextField
          required
          fullWidth
          id="cardType"
          label="Card Type"
          name="cardType"
          autoComplete="cardType"
        />
        <br></br>
        {/* pass type */}
        <FormControl>
          <FormLabel id="radio-buttons-group-label">Pass Type</FormLabel>
          <RadioGroup
            row
            aria-labelledby="radio-buttons-group-label"
            defaultValue="electronic"
            onChange={handlePassTypeChange}
            name="radio-buttons-group">
            <FormControlLabel
              value="electronic"
              control={<Radio />}
              label="Electronic"
            />
            <FormControlLabel
              value="physical"
              control={<Radio />}
              label="Physical"
            />
          </RadioGroup>
        </FormControl>
        <br></br>
        <Button variant="contained" color="primary" type="submit">
          Add
        </Button>
      </Grid>
    </Box>
  );
};

export default AddAttractionForm;
