import { ChangeEvent, useState } from "react";
import { Button, Box, TextField , Modal, Typography } from '@mui/material';

const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    bgcolor: 'background.paper',
    boxShadow: 24,
    p: 2,
  };

interface AddIngredientProps {
    addIngredient: (name: string) => void;
}

export default function AddIngredient({ addIngredient }: AddIngredientProps) {
    const [open, setOpen] = useState<boolean>(false);
    const [name, setName] = useState<string>('');
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);
    const handleAddClick = () => {
        handleClose();
        addIngredient(name);
    }
    const handleNameChange = (e: ChangeEvent<HTMLInputElement>) => {
        setName(e.target.value);
    }

    return (
      <div>
        <Button
            id={'add-ingredient-button'}
            variant="contained"
            sx={{mb: 1}}
            onClick={handleOpen}
            aria-label="Add Ingredient Button"
        >
          Add Ingredient
        </Button>
          <Modal
            open={open}
            onClose={handleClose}
          >
            <Box sx={style}>
              <Typography id="modal-modal-title" variant="h5" sx={{mb: 1}}>
                  Add Ingredient
              </Typography>
              <TextField
                  id="outlined-basic"
                  label="Name"
                  value={name}
                  onChange={handleNameChange}
                  aria-label="Name input"
              />
              <Button 
                  variant="contained" 
                  sx={{ml: 1, height: 56}} 
                  onClick={handleAddClick} 
                  aria-label="Add Button"
              >
                Add
              </Button>
            </Box>
        </Modal>
      </div>
      );
}