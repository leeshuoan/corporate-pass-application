import { useEffect, useState } from 'react'
import Grid from '@mui/material/Grid';
import AttractionCard from './AttractionCard';
import { Container } from '@mui/system';

function Attractions() {
  const [attractions, setAttractions] = useState([])

  useEffect(() => {
    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/attractions/all`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    }).then((response) => response.json())
      .then((data) => {
        console.log(data)
        setAttractions(data.data)
      }).catch((error) => {
        console.log(error)
      })
  }, [])

  const arr = [{'name': 'a'}, {'name': 'b'}, {'name': 'c'}, {'name': 'd'}, {'name': 'e'}, {'name': 'f'}, {'name': 'g'}, {'name': 'h'}, {'name': 'i'}, {'name': 'j'}, {'name': 'k'}, {'name': 'l'}, {'name': 'm'}, {'name': 'n'}, {'name': 'o'}, {'name': 'p'}, {'name': 'q'}, {'name': 'r'}, {'name': 's'}, {'name': 't'}, {'name': 'u'}, {'name': 'v'}, {'name': 'w'}, {'name': 'x'}, {'name': 'y'}, {'name': 'z'}]
  return (
    <Container sx={{p:3}}>
      <Grid 
        container 
        spacing={2}
        justifyContent="center"
        alignItems="center"
      >
        {
          attractions.map((item, index) => {
            return (
              <Grid item lg={4} md={6} key={index}>
                <AttractionCard name={item.name} attractionId={item.attractionId} />
              </Grid>
            )
          })
        }
      </Grid>
    </Container>
  )
}

export default Attractions