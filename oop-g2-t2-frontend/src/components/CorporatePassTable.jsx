import React, { useMemo, useState, useEffect } from "react";
import MaterialReactTable from "material-react-table";
import { Box, Button, Typography } from "@mui/material";
import ThemeProvider from "../theme/index";
import { toast } from "react-toastify";

const CorporatePassTable = () => {
  const [data, setData] = useState([]);

  const fetchAllCorporatePass = () => {
    setData([]);

    let getCorpPasses = fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/corporatePass`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    });
    let getAttractions = fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/attractions/all`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    });

    Promise.all([getCorpPasses, getAttractions]).then(([res1, res2]) => {
      return Promise.all([res1.json(), res2.json()]).then(
        ([response1, response2]) => {
          let attractionObj = {};
          let attractions = response2.data;
          for (let key in attractions) {
            attractionObj[attractions[key]["attractionId"]] =
              attractions[key]["name"];
          }
          for (let idx in response1) {
            response1[idx]["attraction"] =
            attractionObj[response1[idx]["attractionId"]];
          }
          setData(response1);
        }
      );
    });
  };

  const returnPass = (id) => {
    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/gop/returned-pass/` + id, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    }).then((response) => {
      fetchAllCorporatePass();
      toast.success("Pass returned successfully");
    });
  };

  const foundPass = (id) => {
    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/gop/returned-pass/` + id, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    }).then((response) => {
      fetchAllCorporatePass();
      toast.success("Pass found successfully");
    });
  };

  const lostPass = (id) => {
    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/gop/lost-pass/` + id, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    }).then((response) => {
      fetchAllCorporatePass()
      toast.success("Pass returned successfully")
    })
  }

  useEffect(() => {
    fetchAllCorporatePass();
  }, []);

  const columns = useMemo(
    () => [
      {
        accessorKey: "corporatePassId",
        header: "Corporate Pass Card ID",
      },
      {
        accessorKey: "",
        id: "status",
        header: "Status",
        Cell: ({ cell, row }) => (
          <Box
            sx={{
              display: "flex",
              alignItems: "center",
              gap: "1rem",
            }}>
            <ThemeProvider>
              <Typography
                color={
                  row.original.status == "available"
                    ? "success.dark"
                    : row.original.status == "loaned"
                    ? "warning.dark"
                    : "error"
                }>
                {row.original.status}
              </Typography>
            </ThemeProvider>
          </Box>
        ),
      },
      {
        accessorKey: "email",
        header: "Loanee",
      },
      {
        accessorKey: "attraction",
        header: "Attraction",
      },
      {
        accessorKey: "",
        id: "actions", //id is still required when using accessorFn instead of accessorKey
        header: "Actions",
        Cell: ({ cell, row }) => (
          <Box
            sx={{
              display: 'flex',
              alignItems: 'center',
              gap: '1rem',
            }}
          >
            <Button
              color="warning"
              disabled={row.original.status != "loaned"}
              onClick={() => lostPass(row.original.corporatePassId)}
              variant="contained">
              Lost
            </Button>
            <Button color="success"
              disabled={row.original.status != "loaned"}
              onClick={() => returnPass(row.original.corporatePassId)}
              variant="contained">
              Return
            </Button>
            <Button
              color="success"
              disabled={row.original.status != "lost"}
              onClick={() => foundPass(row.original.corporatePassId)}
              variant="contained">
              Found
            </Button>
          </Box>
        ),
      },
    ],
    []
  );

  return (
    <Box m={2}>
      <MaterialReactTable
        columns={columns}
        data={data}
        initialState={{ density: "compact" }}
        renderTopToolbarCustomActions={({ table }) => {
          return (
            <Typography m={1} variant="h6">
              Corporate Passes
            </Typography>
          );
        }}></MaterialReactTable>
    </Box>
  );
};

export default CorporatePassTable;
