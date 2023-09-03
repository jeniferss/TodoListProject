const rowsPerPage = 6;

let TASKS = [
  {
    id: 1,
    title: "Titulo",
    description: "Descricao",
    dueDate: "2023-09-01",
    status: "FAZER",
    category: "Estudos",
    priorityLevel: 1,
  },
  {
    id: 2,
    title: "Titulo",
    description: "Descricao",
    dueDate: "2023-09-01",
    status: "FAZER",
    category: "Lazer",
    priorityLevel: 2,
  },
  {
    id: 3,
    title: "Titulo",
    description: "Descricao",
    dueDate: "2023-09-01",
    status: "FEITO",
    category: "Trabalho",
    priorityLevel: 3,
  },
  {
    id: 4,
    title: "Titulo",
    description: "Descricao",
    dueDate: "2023-09-01",
    status: "FAZENDO",
    category: "Trabalho",
    priorityLevel: 4,
  },
  {
    id: 5,
    title: "Titulo",
    description: "Descricao",
    dueDate: "2023-09-01",
    status: "FEITO",
    category: "Saúde",
    priorityLevel: 5,
  },
  {
    id: 6,
    title: "Titulo",
    description: "Descricao",
    dueDate: "2023-09-01",
    status: "FAZER",
    category: "Saúde",
    priorityLevel: 5,
  },
  {
    id: 7,
    title: "Titulo",
    description: "Descricao",
    dueDate: "2023-09-01",
    status: "FAZENDO",
    category: "Trabalho",
    priorityLevel: 5,
  },
];

let TEMP_TASKS = TASKS;

const NEW_TASK_START_VALUE = {
  id: TASKS.length + 1,
  title: null,
  description: null,
  dueDate: null,
  status: null,
  category: null,
  priorityLevel: null,
};

let NEW_TASK = { ...NEW_TASK_START_VALUE };

const STATUS_COLORS = {
  FAZER: "#ffa500",
  FAZENDO: "#73009a",
  FEITO: "#00cc00",
};

const stringToColor = (text) => {
  let hash = 0;
  text.split("").forEach((char) => {
    hash = char.charCodeAt(0) + ((hash << 5) - hash);
  });

  let color = "#";
  for (let i = 0; i < 3; i++) {
    const value = (hash >> (i * 8)) & 0xff;
    color += value.toString(16).padStart(2, "0");
  }

  return color;
};

const addAlpha = (color, opacity) => {
  var alphaColor = Math.round(Math.min(Math.max(opacity || 1, 0), 1) * 255);
  return color + alphaColor.toString(16).toUpperCase();
};

const createTableRowForTask = (task) => {
  const statusColor = STATUS_COLORS[task.status];
  const statusBackgroundColor = addAlpha(statusColor, 0.3);
  const priorityLevelAndStatusButton = `<span class="dot" style="background-color: ${statusBackgroundColor}; color: ${statusColor}">${task.priorityLevel}</span>`;

  const categoryColor = stringToColor(task.category.toLowerCase());
  const categoryBackgroundColor = addAlpha(categoryColor, 0.3);
  const categoryButton = `<button type="button" class="button-as-chip" style="color: ${categoryColor}; background-color: ${categoryBackgroundColor}">${task.category}</button>`;

  const row = `
        <tr>
            <td>${priorityLevelAndStatusButton}</td>
            <td>${task.title}</td>
            <td>${task.description}</td>
            <td>${task.dueDate}</td>
            <td>${categoryButton}</td>
            <td><i id="${task.id}" class="bi bi-pencil" onclick=onTaskUpdate(${task.id})></i></td>
            <td><i id="${task.id}" class="bi bi-trash" onclick=onTaskDelete(${task.id})></i></td>
        </tr>
    `;

  return row;
};

const onTaskListChange = (start = 0, end = rowsPerPage) => {
  $("#tasks > tbody").empty();

  const PAGE_CONTENT = TASKS.slice(start, end);

  if (PAGE_CONTENT.length > 0) {
    PAGE_CONTENT.forEach((task) => {
      $("#tasks > tbody").append(createTableRowForTask(task));
    });
  } else {
    const noRowsMessage = `<tr align="center"><td colspan="10"><h4>Nenhuma tarefa cadastrada.</h5></td></tr>`;
    $(noRowsMessage).appendTo($("#tasks"));
  }
};

const onPaginationChange = () => {
  const rowsTotal = TASKS.length;
  const numberOfPages = rowsTotal / rowsPerPage;

  $(".pagination > a").each(function () {
    const attrRel = $(this).attr("rel");
    if (attrRel !== undefined) $(this).remove();
  });

  for (page = 1; page < numberOfPages + 1; page++) {
    $(".pagination").append(
      `<a href="#" rel=${page} onclick="onChangePage(${page})">${page}</a>`
    );

    // if (page == 1) {
    //   $("#previous").after(
    //     `<a href="#" rel=${page} onclick="onChangePage(${page})">${page}</a>`
    //   );
    //   continue;
    // }

    // $("#next").before(
    //   `<a href="#" rel=${page} onclick="onChangePage(${page})">${page}</a>`
    // );
  }

  $(".pagination a:first").addClass("active");
};

const handleFormResetValues = () => {
  handleLoadForm(NEW_TASK_START_VALUE);
};

const handleLoadForm = (TASK) => {
  $("#title").val(TASK.title);
  $("#description").val(TASK.description);
  $("#dueDate").val(TASK.dueDate);
  $("#status").val(TASK.status);
  $("#category").val(TASK.category);
  $("#priorityLevel").val(TASK.priorityLevel);
};

const onFormChange = () => {
  $("#title").on("keyup", () => {
    NEW_TASK.title = $("#title").val();
  });

  $("#description").on("keyup", () => {
    NEW_TASK.description = $("#description").val();
  });

  $("#dueDate").on("change", () => {
    NEW_TASK.dueDate = $("#dueDate").val();
  });

  $("#priorityLevel").on("change", () => {
    NEW_TASK.priorityLevel = Number($("#priorityLevel").val());
  });

  $("#status").on("change", () => {
    NEW_TASK.status = $("#status").val();
  });

  $("#category").on("change", () => {
    NEW_TASK.category = $("#category").val();
  });
};

$(onPaginationChange());
$(onTaskListChange());

/*  UPDATE LISTENER */
const onTaskUpdate = (taskId) => {
  const modalTitle = `<h3>Editar Tarefa</h3>`;
  $("#modal-title").empty();
  $("#modal-title").append(modalTitle);

  const updateButton = `<button type="button" class="base-button" id="confirm-task">Editar</button>`;
  $("#modal-button").empty();
  $("#modal-button").append(updateButton);

  $(".overlay").show();

  let taskIndex;

  for (const [index, value] of Object.values(TASKS).entries()) {
    if (value.id == taskId) {
      taskIndex = index;
    }
  }

  NEW_TASK = { ...TASKS[taskIndex] };

  handleLoadForm(NEW_TASK);

  onFormChange();

  $("#confirm-task").on("click", () => {
    TASKS[taskIndex] = { ...NEW_TASK };

    TEMP_TASKS = TASKS;

    $(".overlay").hide();
    handleFormResetValues();

    onPaginationChange();
    onTaskListChange();
  });
};

/* CREATE LISTENER */
const onCreateTask = () => {
  handleLoadForm(NEW_TASK_START_VALUE);

  const modalTitle = `<h3>Criar Tarefa</h3>`;
  $("#modal-title").empty();
  $("#modal-title").append(modalTitle);

  const createButton = `<button type="button" class="base-button" onclick="handleCreateTask()">Criar</button>`;
  $("#modal-button").empty();
  $("#modal-button").append(createButton);

  $(".overlay").show();

  onFormChange();
};

const handleCreateTask = () => {
  TASKS.push(NEW_TASK);

  TEMP_TASKS = TASKS;

  $(".overlay").hide();
  handleFormResetValues();

  onPaginationChange();
  onTaskListChange();
};

/* DELETE LISTENER */
const onTaskDelete = (taskId) => {
  for (const [index, value] of Object.values(TASKS).entries()) {
    if (value.id == taskId) {
      TASKS.splice(index, 1);

      TEMP_TASKS = TASKS;
    }
  }

  onPaginationChange();
  onTaskListChange();
};

/* PAGINATION LISTENER */
const onChangePage = (currentPage) => {
  $(".pagination a").removeClass("active");
  window.event.currentTarget.className += " active";

  const end = currentPage * rowsPerPage;
  const start = end - rowsPerPage;

  onTaskListChange(start, end);
};

$(".bi-funnel-fill").on("click", function () {
  $("#status-dropdown").show();
});

$("#open-modal").on("click", function () {
  onCreateTask();
});

$(".overlay").on("click", function (event) {
  if (!$(event.target).is(".overlay")) return;
  $(".overlay").hide();

  handleFormResetValues();
});

window.onclick = function (event) {
  if (!event.target.matches(".bi-funnel-fill")) {
    $("#status-dropdown").hide();
  }
};

$("#status-dropdown > a").on("click", function () {
  $("#status-dropdown").hide();

  TASKS = TEMP_TASKS;

  if ($(this).attr("rel") !== "LIMPAR") {
    TASKS = TASKS.filter((task) => task.status == $(this).attr("rel"));
  }

  onPaginationChange();
  onTaskListChange();
});
