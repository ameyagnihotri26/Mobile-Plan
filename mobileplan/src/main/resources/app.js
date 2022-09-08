const submit = document.querySelector("#submit");
const update = document.querySelector("#update");
const getData = document.querySelector("#view");
const deleteData = document.querySelector("#delete");
const getByID = document.querySelector("#getByID");

const saveUser = async () => {
  let id = document.getElementById("id").value;
  let name = document.getElementById("name").value;
  let validity = document.getElementById("validity").value;
  let description = document.getElementById("description").value;

  console.log(name, validity, description, id);

  // let response = await fetch('http://localhost:8080/human');

  let response2 = await fetch("http://localhost:8080/mp", {
    method: "POST",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      id: id,
      name: name,
      validity: validity,
      description: description,
    }),
  });

  let respStatus = response2.status;
  if (respStatus === 201) {
    let validationRes = document.getElementById("validationID");
    let text = "Plan Created Successfully";

    validationRes.innerHTML = '<div class="alert alert-success" id ="validationID" role="alert">' + text + '</div>';
  }
  else{
    let validationRes = document.getElementById("validationID");
  
    let text = "Not created";
    validationRes.innerHTML = '<div class="alert alert-danger" id ="validationID" role="alert">' + text + '</div>';
  }

  console.log(response2);
  return false;
};

const deleteUser = async () => {
  let id = document.getElementById("id").value;

  console.log(id);

  let response2 = await fetch('http://localhost:8080/mp/'+id,{
  method : 'DELETE',
  headers: {
    Accept: "application/json",
    "Content-Type": "application/json",
  },
});
let respStatus = response2.status;
  if (respStatus === 200) {
    let validationRes = document.getElementById("validationID");
    let text = "Deleted Successfully";

    validationRes.innerHTML = '<div class="alert alert-success" id ="validationID" role="alert">' + text + '</div>';
  }
  else{
    let validationRes = document.getElementById("validationID");
  
    let text = "ID does not exist";
    validationRes.innerHTML = '<div class="alert alert-danger" id ="validationID" role="alert">' + text + '</div>';
  }
  console.log(response2);
  return false;
};

const getUserByID = async () => {
    let id = document.getElementById("boom").value;

  let response2 = await fetch('http://localhost:8080/mp/'+id,{
    method : 'GET',
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
    },
  });


  let respStatus = response2.status;
  if (respStatus === 200) {
    let validationRes = document.getElementById("validationID");
    let text = " Plan Found";

    validationRes.innerHTML = '<div class="alert alert-success" id ="validationID" role="alert">' + text + '</div>';
  }
  else{
    let validationRes = document.getElementById("validationID");
  
    let text = "Plan not found";
    validationRes.innerHTML = '<div class="alert alert-danger" id ="validationID" role="alert">' + text + '</div>';
  }

  let res = await response2.json();

  let htmlID = document.getElementById("0");
  let htmlvalidity = document.getElementById("3");
  let htmldescription = document.getElementById("2");
  let htmlname = document.getElementById("1");

  console.log(res);

  htmlID.innerHTML = res['id'];
  htmlvalidity.innerText = res['validity'];
  htmldescription.innerText =  res['name'];
  htmlname.innerText = res['description'];


  
};

const getUser = async () => {
  console.log("clicked");
  let response = await fetch("http://localhost:8080/mp");
  let res = await response.json();
  console.log(res);

  var table = document.getElementById("currTable");

  for (var i = 0; i < res.length; i++) {
    var row = table.insertRow(1);
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);
    var cell3 = row.insertCell(1);
    var cell4 = row.insertCell(1);
    cell1.innerHTML = res[i].id;
    cell3.innerHTML = res[i].name;
    cell4.innerHTML = res[i].validity;
    cell2.innerHTML = res[i].description;
  }
};

const updateUser = async () => {
  let id = document.getElementById("updateid").value;
  let name = document.getElementById("updatename").value;
  let validity = document.getElementById("updatevalidity").value;
  let description = document.getElementById("updatedescription").value;

  console.log(name, validity, description, id);

  // let response = await fetch('http://localhost:8080/human');

  let response2 = await fetch("http://localhost:8080/mp", {
    method: "PATCH",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      id: id,
      name: name,
      validity: validity,
      description: description,
    }),
  });

  let respStatus = response2.status;
  if (respStatus === 200) {
    let validationRes = document.getElementById("validationID");
    let text = "Plan Updated Successfully";

    validationRes.innerHTML = '<div class="alert alert-success" id ="validationID" role="alert">' + text + '</div>';
  }
  else{
    let validationRes = document.getElementById("validationID");
  
    let text = "ID does not exist";
    validationRes.innerHTML = '<div class="alert alert-danger" id ="validationID" role="alert">' + text + '</div>';
  }

  console.log(response2);
  return false;
};

if (submit) {
  submit.addEventListener("click", saveUser);
}
if (update) {
  update.addEventListener("click", updateUser);
}

if (getData) {
  getData.addEventListener("click", getUser);
}

if (getByID) {
    getByID.addEventListener("click", getUserByID);
  }

if (deleteData) {
  deleteData.addEventListener("click", deleteUser);
}

