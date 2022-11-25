import * as React from 'react';
import { MenuItem, Button, Box, Grid, TextField, FormControl, FormLabel, RadioGroup, FormControlLabel, Radio, Typography } from '@mui/material';
import { toast } from 'react-toastify';
import { useState } from 'react';

export default function EditAttractionForm({ newAttractionId, newName, newAddress, newCardName, newPassType, newReplacementFee, editSuccess }) {
  const [passType, setPassType] = useState(newPassType);

  const handlePassTypeChange = (event) => {
    setPassType(event.target.value);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    if (data.get('attractionId') == '' || data.get('name') == '' || data.get('address') == '' || data.get('cardName') == '' || data.get('replacementFee') == '') {
      toast.error("Please fill all the fields");
      return;
    }
    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/attractions/updateAttraction`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        attractionId: newAttractionId,
        name: data.get('name'),
        address: data.get('address'),
        cardName: data.get('cardName'),
        replacementFee: data.get('replacementFee'),
        passType: passType,
      })
    }).then((response) => response.json().then((data) => {
      if (data.status === 200) {
        editSuccess("Successfully updated attraction")
      } else {
        toast.error("Error updating attraction");
      }
    })
    )
  };

  return (
    <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 3 }}>
      <Grid container spacing={2}>
        <Grid item xs={12}>
          <Typography variant="h5" component="h2" align="center">
            Edit Attraction
          </Typography>
        </Grid>
        <Grid item xs={12}>
          <TextField
            required
            fullWidth
            id="attractionId"
            label="Attraction ID"
            name="attractionId"
            autoComplete="attractionId"
            value={newAttractionId}
            disabled
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            required
            fullWidth
            id="name"
            label="Name"
            name="name"
            autoComplete="name"
            defaultValue={newName}
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            required
            fullWidth
            id="address"
            label="Address"
            name="address"
            autoComplete="address"
            defaultValue={newAddress}
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            required
            fullWidth
            id="cardName"
            label="Card Name"
            name="cardName"
            autoComplete="cardName"
            defaultValue={newCardName}
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            required
            fullWidth
            id="replacementFee"
            label="Replacement Fee"
            name="replacementFee"
            autoComplete="replacementFee"
            defaultValue={newReplacementFee}
          />
        </Grid>
        <Grid item xs={12}>
          <FormControl>
            <FormLabel id="radio-buttons-group-label">Pass Type</FormLabel>
            <RadioGroup
              row
              aria-labelledby="radio-buttons-group-label"
              defaultValue={newPassType}
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
        </Grid>
      </Grid>
      <Button
        type="submit"
        fullWidth
        variant="contained"
        sx={{ mt: 3, mb: 2 }}
      >
        Submit
      </Button>
    </Box>
  );
};
