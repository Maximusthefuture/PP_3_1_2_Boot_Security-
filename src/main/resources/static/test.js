const requestURL = 'http://localhost:8080/api/admin';

const usersTableNavLink = document.getElementById("horizontal_navigation-users_table");
const newUserNawLink = document.getElementById("horizontal_navigation-new_user");
const allUsersTable = document.querySelector(".all-users-table");


//Users table

const renderUsers = (users) => {
    if (users.length > 0) {
        let temp = '';
        users.forEach((user) => {
            temp += `
                <tr>
                    <td> ${user.id} </td>
                    <td> ${user.name} </td>
                    <td> ${user.lastName} </td>
                    <td> ${user.email} </td>
                    <td> ${user.age} </td>
                    <td> ${user.roles.map((role) => role.name === "USER" ? " User" : " Admin")} </td>
                    <td> <button type="button" class="btn btn-info" id="btn-edit-modal-call" data-toggle="modal" data-target="modal-edit"
                    data-id="${user.id}">Edit</button></td>
                    <td> <button type="button" class="btn btn-danger" id="btn-delete-modal-call" 
                    data-id="${user.id}">Delete</button></td>
                </tr>
        `
        })
        allUsersTable.innerHTML = temp;
    }
}

// Getting the data of all users with fetch and filling in the table with renderUsers

function getAllUsers () {
    fetch(requestURL, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(res => res.json())
        .then(data => {
            renderUsers(data);
        })
}

getAllUsers();

//addNewUser
//Form for new user

const addUserForm = document.querySelector(".add-user-form");

// form for NEW user

const FormName = document.getElementById("add-user-form-name");
const FormLastName = document.getElementById("add-user-form-lastName");
const FormEmail = document.getElementById("add-user-form-email");
const FormPassword = document.getElementById("add-user-form-password");
const FormAge = document.getElementById("add-user-form-age");
const FormRoles = document.getElementById("add-user-form-roles");
// submit
const addButtonSubmit = document.getElementById("add-btn-submit");

//role generation
function getRolesFromAddUserForm() {
    let roles = Array.from(FormRoles.selectedOptions)
        .map(option => option.value);
    let rolesToAdd = [];
    if (roles.includes("1")) {
        let role1 = {
            id: 1,
            name: "Admin"
        }
        rolesToAdd.push(role1);
    }
    if (roles.includes("2")) {
        let role2 = {
            id: 2,
            name: "User"
        }
        rolesToAdd.push(role2);
    }
    return rolesToAdd;
}

addUserForm.addEventListener("submit", (e) => {
    e.preventDefault();
    fetch(requestURL + "/adduser", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: FormName.value,
            lastName: FormLastName.value,
            email: FormEmail.value,
            password: FormPassword.value,
            age: FormAge.value,
            roles: getRolesFromAddUserForm()
        })
    })
        .then(() => {
            usersTableNavLink.click();
            location.reload();
        });
})

//edit
const modalEditExitBtn = document.getElementById("exit_btn-modal-edit");
const modalEditCloseBtn = document.getElementById("close_btn-modal-edit");
const modalEditSubmitBtn = document.getElementById("submit_btn-modal-edit");
const editUsersRoles = document.getElementById("edit-rolesSelect");
const editRoleAdminOption = document.getElementById("edit-role_admin");
const editRoleUserOption = document.getElementById("edit-role_user");

//delete
const deleteRoleAdminOption = document.getElementById("delete-role_admin");
const deleteRoleUserOption = document.getElementById("delete-role_user");
const modalDeleteSubmitBtn = document.getElementById("submit_btn-modal-delete");
const modalDeleteExitBtn = document.getElementById("exit_btn-modal-delete");
const modalDeleteCloseBtn = document.getElementById("close_btn-modal-delete");


let getDataOfCurrentUser = (id) => {
    return fetch(requestURL + "/" + id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    })
        .then(res => res.json())
        .then(dataUser => {
            let user = {
                id: dataUser.id,
                name: dataUser.name,
                lastName: dataUser.lastName,
                email: dataUser.email,
                password: dataUser.password,
                age: dataUser.age,
                roles: dataUser.roles
            }
            console.log(user)
        })
}

function getRolesFromEditUserForm() {
    let roles = Array.from(editUsersRoles.selectedOptions)
        .map(option => option.value);
    let rolesToEdit = [];
    if (roles.includes("1")) {
        let role1 = {
            id: 2,
            name: "Admin"
        }
        rolesToEdit.push(role1);
    }
    if (roles.includes("2")) {
        let role2 = {
            id: 1,
            name: "User"
        }
        rolesToEdit.push(role2);
    }
    return rolesToEdit;
}

//tracking submit click Edit/Delete
allUsersTable.addEventListener("click", e => {
    e.preventDefault();
    let delButtonIsPressed = e.target.id === 'btn-delete-modal-call';
    let editButtonIsPressed = e.target.id === 'btn-edit-modal-call';

//getting data for DELETE user

    const deleteUsersId = document.getElementById("delete-id")
    const deleteUsersName = document.getElementById("delete-name")
    const deleteUsersLastName = document.getElementById("delete-lastName")
    const deleteUsersEmail = document.getElementById("delete-email")
    const deleteUsersAge = document.getElementById("delete-age")
    const deleteUsersPassword= document.getElementById("delete-password")
   

    if (delButtonIsPressed) {
        let currentUserId = e.target.dataset.id;
        fetch(requestURL + "/" + currentUserId, {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json;charset=UTF-8'
            }
        })
            .then(res => res.json())
            .then(user => {
                deleteUsersId.value = user.id;
                deleteUsersName.value = user.name;
                deleteUsersLastName.value = user.lastName;
                deleteUsersEmail.value = user.email;
                deleteUsersPassword.value = user.password;
                deleteUsersAge.value = user.age;

                let deleteRoles = user.roles.map(role => role.name)
                deleteRoles.forEach(
                    role => {
                        if (role === "ADMIN") {
                            deleteRoleAdminOption.setAttribute('selected', "selected");

                        } else if (role === "USER") {
                            deleteRoleUserOption.setAttribute('selected', "selected");
                        }
                    })
            })
        $('#modal-delete').modal('show');

        modalDeleteSubmitBtn.addEventListener("click", e => {
            e.preventDefault();
            fetch(`${requestURL}/delete?id=${currentUserId}`, {
                method: 'DELETE',
            })
                .then(res => res.json());
            modalDeleteExitBtn.click();
            getAllUsers();
            location.reload();
        })
    }

//getting data for EDIT user

    const editUsersId = document.getElementById("edit-id");
    const editUsersName = document.getElementById("edit-name");
    const editUsersLastName = document.getElementById("edit-lastName");
    const editUsersEmail= document.getElementById("edit-email");
    const editUsersPassword = document.getElementById("edit-password");
    const editUsersAge = document.getElementById("edit-age");

    if (editButtonIsPressed) {
        let currentUserId = e.target.dataset.id;
        fetch(requestURL + "/" + currentUserId, {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json;charset=UTF-8'
            }
        })
            .then(res => res.json())
            .then(user => {

                editUsersId.value = user.id;
                editUsersName.value = user.name;
                editUsersLastName.value = user.lastName;
                editUsersEmail.value = user.email;
                editUsersPassword.value = user.password;
                editUsersAge.value = user.age;

                let editRoles = user.roles.map(role => role.name)
                editRoles.forEach(
                    role => {
                        if (role === "ADMIN") {
                            editRoleAdminOption.setAttribute('selected', "selected");

                        } else if (role === "USER") {
                            editRoleUserOption.setAttribute('selected', "selected");
                        }
                    })
            })
        $('#modal-edit').modal('show');

        modalEditSubmitBtn.addEventListener("click", e => {
            e.preventDefault();
            let user = {
                id: editUsersId.value,
                name: editUsersName.value,
                lastName: editUsersLastName.value,
                email: editUsersEmail.value,
                password: editUsersPassword.value,
                age: editUsersAge.value,
                roles: getRolesFromEditUserForm()
            }
            fetch(`${requestURL}/update/${currentUserId}`, {
                
                method: 'PUT',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json;charset=UTF-8'
                },
                body: JSON.stringify(user)
            })
                .then(res => console.log(res));
            modalEditExitBtn.click();
            getAllUsers();
            location.reload();
        })
    }
})

//close modal window edit
let removeSelectedRolesFromEditDoc = () => {
    if (editRoleAdminOption.hasAttribute('selected')) {
        editRoleAdminOption.removeAttribute('selected')
    }
    if (editRoleUserOption.hasAttribute('selected')) {
        editRoleUserOption.removeAttribute('selected')
    }
}
modalEditExitBtn.addEventListener("click", e => {
    e.preventDefault();
    removeSelectedRolesFromEditDoc();
})
modalEditCloseBtn.addEventListener("click", e => {
    e.preventDefault();
    removeSelectedRolesFromEditDoc();
})

//close modal window delete
let removeSelectedRolesFromDeleteDoc = () => {
    if (deleteRoleAdminOption.hasAttribute('selected')) {
        deleteRoleAdminOption.removeAttribute('selected')
    }
    if (deleteRoleUserOption.hasAttribute('selected')) {
        deleteRoleUserOption.removeAttribute('selected')
    }
}
modalDeleteExitBtn.addEventListener("click", e => {
    e.preventDefault();
    removeSelectedRolesFromDeleteDoc();
})
modalDeleteCloseBtn.addEventListener("click", e => {
    e.preventDefault();
    removeSelectedRolesFromDeleteDoc();
})


//information about SOLO USER who was login

const userPanelData = document.getElementById("user_panel-data");
const authorisedUserData = document.getElementById("authorised_user-data");

let currentUser = () => {
    fetch ("http://localhost:8080/api/user", {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(res => res.json())
        .then(user => {
            if (user != null) {
                userPanelData.innerHTML = `
                    <tr>
                        <td> ${user.id} </td>
                        <td> ${user.name} </td>
                        <td> ${user.lastName} </td>
                        <td> ${user.email} </td>
                        <td> ${user.roles.map((role) => role.name === "USER" ? " User" : " Admin")} </td>
                    </tr>
                `
                authorisedUserData.innerHTML = `
                    <p class="d-inline font-weight-bold">${user.username} With role ${user.roles.map((role) => role.name === "USER" ? " User" : " Admin")}</p>`
            }
        })
}
currentUser();