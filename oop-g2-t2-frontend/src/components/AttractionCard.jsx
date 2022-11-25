import * as React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import { Button, CardActions, CardActionArea } from '@mui/material';
import { useNavigate } from 'react-router-dom';

export default function AttractionCard({name, attractionId}) {
  const navigate = useNavigate()

  const handleMoreInfo = () => {
    const url = "https://www.mandai.com/en/mandai.html"
    window.open(url, '_blank')
  }

  const handleBook = () => {
    navigate(`/employee/book/${attractionId}`)
  }

  return (
    <Card sx={{ maxWidth: 500, margin: 'auto' }}>
      <CardActionArea onClick={handleBook}>
        <CardMedia
          component="img"
          height="200"
          image="/SSSLogo1.png"
          alt="SSSLogo1"
        />
        <CardContent>
          <Typography gutterBottom variant="h5" component="div">
            {name}
          </Typography>
          <Typography variant="body2" color="text.secondary">
            Lorem ipsum dolor sit, amet consectetur adipisicing elit. Tempore expedita, repellat, pariatur officiis ea qui fugiat et commodi, cumque ipsam deserunt maiores. Debitis quos ea iure magnam cumque, rerum corrupti!
          </Typography>
        </CardContent>
      </CardActionArea>
      <CardActions>
        <Button size="small" color="primary" onClick={handleMoreInfo}>
          More Info
        </Button>
      </CardActions>
    </Card>
  );
}
