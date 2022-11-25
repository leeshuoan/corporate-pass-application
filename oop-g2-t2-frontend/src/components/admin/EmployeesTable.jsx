import React, { useMemo, useState, useEffect } from 'react';
import MaterialReactTable from 'material-react-table';
import { Button, Box, Switch } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import DeleteIcon from '@mui/icons-material/Delete';
import TransitionsModal from '../utils/TransitionsModal';
import EmployeeForm from './EmployeeForm'
import EditEmployeeForm from './EditEmployeeForm';
import { toast } from 'react-toastify';

const EmployeesTable = () => {
  const [data, setData] = useState([]);
  const [rowData, setRowData] = useState([]);
  const [open, setOpen] = useState(false);
  const [openEditModal, setOpenEditModal] = useState(false);
  const handleClose = () => setOpen(false);

  const handleOpen = () => {
    setOpen(true)
  }

  const fetchAllUser = () => {
    setData([]);
    fetch("http://localhost:8080/api/v1/user/all-with-role", {
      method: "GET",
      headers: {
        'Content-Type': 'application/json',
      }
    }).then((response) => response.json().then((data) => {
      console.log(data);
      setData(data.map((user) => {
        user.roles = user.roles.map((role) => role.name).join(', ')
        return user
      }))
    }))
  }

  const closeEditModal = () => {
    setOpenEditModal(false);
  };

  const handleSuccess = (message) => {
    fetchAllUser()
    toast.success(message)
    handleClose()
  }

  const editSuccess = (message) => {
    toast.success(message)
    closeEditModal();
    fetchAllUser();
  };

  const editUser = (row) => {
    setRowData(row)
    setOpenEditModal(true);
  };

  const fetchDeleteUsers = (emails) => {
    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/admin/delete`, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(emails),
    }).then((response) => {
      if (response.status === 200) {
        fetchAllUser();
        handleSuccess("Successfully deleted employee");
      } else {
        toast.error('Error deleting employee')
      }
    });
  };
  // ========================import as csv , incomplete(not working)========================

  // const handleFileUpload = (event) => {
  //   const file = event.target.files[0];
  //   console.log(file);
  //   const formData = new FormData();
  //   formData.append('file', file[0]);
  //   console.log(formData)
  // fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/admin/upload`, {
  //   method: 'POST',
  //   headers: {
  //   },
  //   body: formData
  // }).then((response) => {
  //   if (response.status === 200) {
  //     fetchAllUser()

  //     handleSuccess("Successfully uploaded employees")
  //   } else {
  //     alert('Error uploading employees')
  //   }
  // })
  // }
  //========================================================================
  const handleBan = (email) => {
    fetch(
      `${import.meta.env.VITE_DOMAIN_NAMEPATH}/admin/banEmployee/${email}`,
      {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
      }
    ).then((response) => {
      if (response.status === 200) {
        handleSuccess("Successfully banned employee");
      } else {
        toast.error('Error banning employee')
      }
    });
  };

  const handleUnban = (email) => {
    fetch(
      `${import.meta.env.VITE_DOMAIN_NAMEPATH}/admin/unbanEmployee/${email}`,
      {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
      }
    ).then((response) => {
      if (response.status === 200) {
        handleSuccess("Successfully unbanned employee");
      } else {
        toast.error('Error unbanning employee')
      }
    });
  };

  useEffect(() => {
    fetchAllUser();
  }, []);

  //should be memoized or stable
  const columns = useMemo(
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
            <Button onClick={() => editUser(row.original)} variant="contained">
              Edit
            </Button>
          </Box>
        ),
      },
      {
        accessorKey: "banned",
        header: "Ban",
        enableEditing: false,
        Cell: ({ row }) => (
          <Switch
            color="secondary"
            checked={row.original.banned == 1}
            onChange={(event) => {
              if (event.target.checked) {
                handleBan(row.original.email);
              } else {
                handleUnban(row.original.email);
              }
            }}></Switch>
        ),
      },
      {
        accessorKey: "email", //access nested data with dot notation
        header: "Email",
        enableEditing: false,
      },
      {
        accessorKey: "username",
        header: "Username",
      },
      {
        accessorKey: "name", //normal accessorKey
        header: "Name",
      },
      {
        accessorKey: "roles",
        header: "Roles",
      },
      {
        accessorKey: "contact",
        header: "Contact",
      },
    ],
    []
  );

  return (
    <Box m={2}>
      <TransitionsModal open={open} handleClose={handleClose}>
        <EmployeeForm handleSuccess={handleSuccess} />
      </TransitionsModal>

      <TransitionsModal open={openEditModal} handleClose={closeEditModal}>
        {
          <>
            <EditEmployeeForm
              newEmail={rowData.email}
              newUsername={rowData.username}
              newName={rowData.name}
              newContact={rowData.contact}
              newRoles={rowData.roles}
              editSuccess={editSuccess}
            />
          </>
        }
      </TransitionsModal>

      {/* TODO: Add detail panel (expanding) feature to show employee loan stats https://www.material-react-table.com/docs/guides/detail-panel */}
      <MaterialReactTable
        enableRowSelection
        columns={columns}
        data={data}
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
          const handleDelete = () => {
            const emailsArr = table
              .getSelectedRowModel()
              .flatRows.map((row) => {
                return row.getValue("email");
              });
            fetchDeleteUsers(emailsArr);
            table.resetRowSelection();
          };
          //
          return (
            <div>
              <div style={{ display: "flex", gap: "0.5rem", margin: "10px" }}>
                {/* <Button variant="contained" component="label">
                  <PublishIcon fontSize="small" />
                  Import With CSV
                  <input
                    type="file"
                    id="employeeFile"
                    accept=".csv"
                    onChange={handleFileUpload}
                    hidden
                  />
                </Button> */}
                
                <Button onClick={handleOpen} variant="contained">
                  <AddIcon fontSize="small" />
                  Add
                </Button>
                <Button
                  color="secondary"
                  disabled={table.getSelectedRowModel().flatRows.length === 0}
                  onClick={handleDelete}
                  variant="contained">
                  <DeleteIcon fontSize="small" /> Delete
                </Button>
              </div>
            </div>
          );
        }}
      />
    </Box>
  );
};

export default EmployeesTable;
