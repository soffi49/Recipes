import { ChangeEvent, useState } from "react";
import { Button, Box, TextField , Modal, Typography, IconButton } from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';

const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    bgcolor: 'background.paper',
    boxShadow: 24,
    p: 2,
  };

interface EditIngredientProps {
    id: number;
    oldName: string;
    editIngredient: (name: string, id: number) => void;
}

export default function EditIngredient({ id, oldName, editIngredient }: EditIngredientProps) {
    const [open, setOpen] = useState<boolean>(false);
    const [name, setName] = useState<string>('');
    const handleOpen = () => {
        setOpen(true)
        setName(oldName);
    };
    const handleClose = () => setOpen(false);
    const handleSaveClick = () => {
        handleClose();
        editIngredient(name, id);
    }
    const handleNameChange = (e: ChangeEvent<HTMLInputElement>) => {
        setName(e.target.value);
    }

    return (
      <>
          <IconButton aria-label="Edit button" onClick={handleOpen}>
            <EditIcon />
          </IconButton>
          <Modal
            open={open}
            onClose={handleClose}
          >
            <Box sx={style}>
              <Typography id="modal-modal-title" variant="h5" sx={{mb: 1}}>
                  Edit Ingredient
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
                  onClick={handleSaveClick} 
                  aria-label="Save Button"
              >
                Save
              </Button>
            </Box>
        </Modal>
      </>
      );
}