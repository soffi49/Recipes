import React, { ChangeEvent, useState } from "react";
import {
  Grid,
  Button,
  TextField,
  Select,
  FormControl,
  InputLabel,
  MenuItem,
  SelectChangeEvent,
} from "@mui/material";
import { RecipesTags } from "./RecipeFilters.constants";

interface RecipeFiltersProps {
  onFilterClick: (name: string, tag: string) => void;
}

export const RecipesFilters = ({ onFilterClick }: RecipeFiltersProps) => {
  const [name, setName] = useState("");
  const [tag, setTag] = useState("");

  const handleNameChange = (event: ChangeEvent<HTMLInputElement>) =>
    setName(event.target.value);

  const handleTagChange = (event: SelectChangeEvent<string>) =>
    setTag(event.target.value);

  return (
    <Grid
      container
      spacing={1}
      direction={"row"}
      sx={{
        maxWidth: "50%",
        marginBottom: 2,
        flexGrow: 1,
      }}
    >
      <Grid item xs={4}>
        <TextField
          value={name}
          onChange={handleNameChange}
          sx={{ background: "white" }}
          label="Name"
        />
      </Grid>
      <Grid item xs={4}>
        <FormControl fullWidth sx={{ background: "white" }}>
          <InputLabel id="demo-simple-select-label">Tag</InputLabel>
          <Select
            labelId="demo-simple-select-label"
            id="demo-simple-select"
            value={tag}
            label="Tag"
            onChange={handleTagChange}
          >
            {RecipesTags.map((tag) => (
              <MenuItem value={tag}>{tag}</MenuItem>
            ))}
          </Select>
        </FormControl>
      </Grid>
      <Grid
        item
        xs={4}
        sx={{
          justifyContent: "center",
          margin: "auto",
          alignItems: "center",
        }}
      >
        <Button
          variant="contained"
          onClick={() => onFilterClick(name, tag)}
          sx={{
            width: "80%",
            marginLeft: 2,
            height: "56px",
          }}
        >
          Filter
        </Button>
      </Grid>
    </Grid>
  );
};
