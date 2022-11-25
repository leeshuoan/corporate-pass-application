import React, { useMemo, useState, useEffect } from "react";
import MaterialReactTable from "material-react-table";
import AddIcon from "@mui/icons-material/Add";
import { Box, Button, Typography, Modal } from "@mui/material";
import ThemeProvider from "../../theme/index";
import AddCorporatePassForm from "./AddCorporatePassForm";
import AddAttractionForm from "./AddAttractionForm";
import EditAttractionForm from "./EditAttractionForm";
import EditLoanPolicyForm from "./EditLoanPolicyForm";
import TransitionsModal from "../utils/TransitionsModal";
import { toast } from "react-toastify";

const AddCorporatePass = () => {
  const [data, setData] = useState([]);
  const [rowData, setRowData] = useState([]);
  const [attractionData, setAttractionData] = useState([]);
  const [openAddPass, setOpenAddPass] = useState(false);
  const [openEditModal, setOpenEditModal] = useState(false);
  const [openAddAttraction, setOpenAddAttraction] = useState(false);

  const [openEditLoanPolicy, setEditLoanPolicy] = useState(false);
  const handleOpenEditBooking = () => setEditLoanPolicy(true);
  const handleCloseEditBooking = () => setEditLoanPolicy(false);

  const handleOpenAddPass = () => setOpenAddPass(true);
  const handleCloseAddPass = () => setOpenAddPass(false);
  const handleOpenAddAttraction = () => setOpenAddAttraction(true);
  const handleCloseAddAttraction = () => {
    setOpenAddAttraction(false);
    fetchAllCorporatePass();
  }

  const closeEditModal = () => {
    setOpenEditModal(false);
  };

  const editSuccess = (message) => {
    toast.success(message);
    closeEditModal();
    fetchAllCorporatePass();
  };

  const editAttraction = (row) => {
    setRowData(row);
    setOpenEditModal(true);
  };

  const style = {
    position: "absolute",
    top: "50%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    width: 400,
    bgcolor: "background.paper",
    border: "2px solid #000",
    boxShadow: 24,
    p: 4,
  };

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
          setAttractionData(attractions);
        }
      );
    });
  };

  const lostPass = (id) => {
    toast.info("Processing pass lost request...");
    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/gop/lost-pass/` + id, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    }).then((response) => {
      fetchAllCorporatePass()
      toast.success("Pass has been updated to Lost successfully")
    })
  }

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

  useEffect(() => {
    fetchAllCorporatePass();
  }, []);

  const corporatePassColumns = useMemo(
    () => [
      {
        accessorKey: "corporatePassId",
        header: "Corporate Pass ID",
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
              display: "flex",
              alignItems: "center",
              gap: "1rem",
            }}>
            <Button
              color="warning"
              disabled={row.original.status != "loaned"}
              onClick={() => lostPass(row.original.corporatePassId)}
              variant="contained">
              Lost
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

  const attractionColumns = useMemo(
    () => [
      {
        accessorKey: "",
        id: "actions",
        header: "Actions",
        size: 10,
        Cell: ({ cell, row }) => (
          <Box
            sx={{
              display: "flex",
              alignItems: "center",
              gap: "1rem",
            }}>
            <Button
              onClick={() => editAttraction(row.original)}
              variant="contained">
              Edit
            </Button>
          </Box>
        ),
      },
      {
        accessorKey: "attractionId", //access nested data with dot notation
        header: "Attraction ID",
        enableEditing: false,
      },
      {
        accessorKey: "name", //normal accessorKey
        header: "Name",
      },
      {
        accessorKey: "address",
        header: "Address",
      },
      {
        accessorKey: "cardName",
        header: "Card Name",
      },
      {
        accessorKey: "passType",
        header: "Pass Type",
      },
      {
        accessorKey: "replacementFee",
        header: "Replacement Fee",
      },
    ],
    []
  );

  return (
    <Box m={2}>
      <TransitionsModal open={openEditModal} handleClose={closeEditModal}>
        {
          <>
            <EditAttractionForm
              newAttractionId={rowData.attractionId}
              newName={rowData.name}
              newAddress={rowData.address}
              newCardName={rowData.cardName}
              newPassType={rowData.passType}
              newReplacementFee={rowData.replacementFee}
              editSuccess={editSuccess}
            />
          </>
        }
      </TransitionsModal>

      <MaterialReactTable
        columns={corporatePassColumns}
        data={data}
        initialState={{ density: "compact" }}
        renderTopToolbarCustomActions={({ table }) => {
          return (
            <Box sx={{ display: "flex" }}>
              <Button onClick={handleOpenAddAttraction} variant="contained">
                <AddIcon fontSize="small" />
                Add Attraction
              </Button>
              &nbsp;&nbsp;
              <Button onClick={handleOpenAddPass} variant="contained">
                <AddIcon fontSize="small" />
                Add Corporate Pass
              </Button>
              <Modal
                open={openAddPass}
                onClose={handleCloseAddPass}
                aria-labelledby="modal-pass-title"
                aria-describedby="modal-pass-description">
                <Box sx={style}>
                  <AddCorporatePassForm
                    fetchAllCorporatePass={fetchAllCorporatePass}
                    handleCloseAddPass={handleCloseAddPass}
                  />
                </Box>
              </Modal>
              <Modal
                open={openAddAttraction}
                onClose={handleCloseAddAttraction}
                aria-labelledby="modal-attraction-title"
                aria-describedby="modal-attraction-description">
                <Box sx={style}>
                  <AddAttractionForm
                    handleCloseAddAttraction={handleCloseAddAttraction}
                  />
                </Box>
              </Modal>
              &nbsp;&nbsp;
              
              <Button onClick={handleOpenEditBooking} variant="contained">
                Edit loan policy
              </Button>
              <Modal
                open={openEditLoanPolicy}
                onClose={handleCloseEditBooking}
                aria-labelledby="modal-booking-title"
                aria-describedby="modal-booking-description">
                <Box sx={style}>
                  <EditLoanPolicyForm
                    handleCloseEditBooking={handleCloseEditBooking}
                  />
                </Box>
              </Modal>
              
            </Box>
          );
        }}></MaterialReactTable>

      <Box sx={{ mt: 5 }}>
        <MaterialReactTable
          columns={attractionColumns}
          data={attractionData}
          muiTablePaperProps={{ sx: { borderRadius: 2 } }}
          muiTableHeadRowProps={{ sx: { bgcolor: "background.paper" } }}
          muiTableBodyRowProps={{ sx: { bgcolor: "background.paper" } }}
          muiTopToolbarProps={{
            sx: {
              bgcolor: "background.paper",
              borderTopLeftRadius: 16,
              borderTopRightRadius: 16,
            },
          }}
          muiBottomToolbarProps={{
            sx: {
              bgcolor: "background.paper",
              borderBottomLeftRadius: 16,
              borderBottomRightRadius: 16,
            },
          }}
          muiTableBodyProps={{ sx: { bgcolor: "background.paper" } }}
          state={{ isLoading: !data.length }}
          positionToolbarAlertBanner="bottom"
          renderTopToolbarCustomActions={({ table }) => {
            return (
              <>
                <Typography variant="h6">Attractions</Typography>
              </>
            );
          }}
        />
      </Box>
    </Box>
  );
};

export default AddCorporatePass;
