import axios from "axios";
import { toast } from "react-toastify";
import { server } from "../constants/constants";
import { RecipeDetails } from "../models/models";

const sortArrayByName = ( array : any[] ) => {
  array.sort((a : any, b : any) => a.name.localeCompare(b.name))
}


export async function getIngredientsApi(page: number, limit: number) {
  const key = "" + sessionStorage.getItem("key");
  try {
    const response = await axios.post(
      `${server}/ingredients/all?page=${page}&limit=${limit}`,
      {},
      {
        headers: {
          security_header: key,
        },
      }
    );
    sortArrayByName(response.data['ingredients'])

    return response.data;
  } catch (error) {
    toast.error(String(error));
    throw error;
  }
}
export async function getFilteredIngredientsApi(
  page: number,
  limit: number,
  filter: string
) {
  if (filter === "" || filter === undefined)
    return getIngredientsApi(page, limit);

  const key = "" + sessionStorage.getItem("key");
  try {
    const response = await axios.post(
      `${server}/ingredients/all?page=${page}&limit=${limit}`,
      {
        name: filter,
      },
      {
        headers: {
          security_header: key,
        },
      }
    );
    sortArrayByName(response.data['ingredients'])

    return response.data;
  } catch (error) {
    throw error;
  }
}

export async function addIngredientApi(name: string) {
  const key = "" + sessionStorage.getItem("key");
  try {
    await axios.post(
      `${server}/ingredients`,
      {
        name: name,
      },
      {
        headers: {
          security_header: key,
        },
      }
    );
  } catch (error) {
    toast.error(String(error));
    throw error;
  }
}

export async function deleteIngredientApi(id: number) {
  const key = "" + sessionStorage.getItem("key");
  try {
    await axios.delete(`${server}/ingredients/${id}`, {
      headers: {
        security_header: key,
      },
    });
  } catch (error) {
    toast.error(String(error));
    throw error;
  }
}

export async function editIngredientApi(name: string, id: number) {
  const key = "" + sessionStorage.getItem("key");
  try {
    await axios.put(
      `${server}/ingredients`,
      {
        id: id,
        name: name,
      },
      {
        headers: {
          security_header: key,
        },
      }
    );
  } catch (error) {
    toast.error(String(error));
    throw error;
  }
}

export async function deleteRecipeApi(id: number) {
  const key = "" + sessionStorage.getItem("key");
  try {
    await axios.delete(`${server}/recipes/${id}`, {
      headers: {
        security_header: key,
      },
    });
  } catch (error) {
    toast.error(String(error));
    throw error;
  }
}

export async function getRecipesApi(
  page: number,
  limit: number,
  name?: string,
  tag?: string
) {
  const key = "" + sessionStorage.getItem("key");
  try {
    const response = await axios.post(
      `${server}/recipes/all?page=${page}&limit=${limit}`,
      {
        name: name ?? null,
        tags: tag ? [tag] : null,
      },
      {
        headers: {
          security_header: key,
        },
      }
    );
    sortArrayByName(response.data['recipes'])

    return response.data;
  } catch (error) {
    toast.error(String(error));
    throw error;
  }
}

export async function editRecipeApi(recipe: RecipeDetails, id: number) {
  const key = "" + sessionStorage.getItem("key");
  try {
    const response = await axios.put(
      `${server}/recipes`,
      {
        id: recipe.id,
        name: recipe.name,
        instructions: recipe.instructions,
        ingredients: recipe.ingredients,
        tags: recipe.tags,
      },
      {
        headers: {
          security_header: key,
        },
      }
    );
    return response.data;
  } catch (error) {
    toast.error(String(error));
    throw error;
  }
}

export async function addRecipeApi(recipe: RecipeDetails) {
  const key = "" + sessionStorage.getItem("key");
  console.log(recipe.ingredients);
  try {
    const response = await axios.post(
      `${server}/recipes`,
      {
        name: recipe.name,
        instructions: recipe.instructions,
        ingredients: recipe.ingredients,
        tags: recipe.tags,
      },
      {
        headers: {
          security_header: key,
        },
      }
    );
    return response.data;
  } catch (error) {
    toast.error(String(error));
    throw error;
  }
}

export async function registerApi(username: string, password: string) {
  try {
    const response = await axios.post(`${server}/register`, {
      username: username,
      password: password,
    });
    return response;
  } catch (error: unknown) {
      if(error instanceof Error) {
        toast.error(String(error.message));
      }
    throw error;
  }
}
