<%@ include file="../../taglibs.inc"%>
<!DOCTYPE html>
<html>

<head>
<title>${title}</title>

<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<script src="http://yui.yahooapis.com/3.4.1/build/yui/yui-debug.js"></script>

<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/3.4.1/build/cssgrids/grids-min.css">

<style scoped>
body {
  margin: auto; /* center in viewport */
  width: 1440px;
}

ol,ul {
  list-style: none outside none;
}

#place-app {
  margin: 1em;
}

#place-list,#place-stats {
  margin: 1em auto;
  text-align: left;
  width: 450px;
}

#place-list {
  list-style: none;
  padding: 0;
}

#place-stats,.place-clear {
  color: #777;
}

.place-clear {
  float: right;
}

.place-edit,.editing .place-view {
  display: none;
}

.editing .place-edit {
  display: block;
}

.place-form li {
  padding-top: 15px;
}

.place-form-field { #
  display: block;
  font-family: Helvetica, sans-serif;
  font-size: 20px;
  line-height: normal;
  margin: 5px auto 0;
  padding: 6px;
  width: 420px;
}

.place-form-label {
  float: none;
  witdh: auto;
  clear: left;
  font-size: 13px;
  font-weight: 700;
}

.place-form-note {
  color: #999999;
  font-size: 13px;
  font-style: normal;
}

.place-item {
  border-bottom: 1px dotted #cfcfcf;
  font-size: 20px;
  padding: 6px;
  position: relative;
}

.place-title {
  color: #444;
  font-size: 20px;
  font-weight: bold;
}

.place-remaining {
  color: #333;
  font-weight: bold;
}

.place-remove {
  position: absolute;
  right: 0;
  top: 12px;
}

.place-remove-icon { /*
    Delete icon courtesy of The Noun Project:
    http://thenounproject.com/noun/delete/
    */
  background: url(../assets/app/remove.png) no-repeat;
  display: block;
  height: 16px;
  opacity: 0.6;
  visibility: hidden;
  width: 23px;
}

.place-remove:hover .place-remove-icon {
  opacity: 1.0;
}

.place-hover .place-remove-icon,.place-remove:focus .place-remove-icon {
  visibility: visible;
}

.editing .place-remove-icon {
  visibility: hidden;
}

.invisible {
  display:none;
  visibility: hidden;
}
</style>

</head>

<body>

<div class="yui3-g">
    <div class="yui3-u-1-3">
<div id="place-app">
    <p class="place-title">Places</p>

    <div id="new-place">
      <button id="new-place-button" class="">Add Place</button>
    </div>

  <div id="place-form" class="invisible">
      <button id="save-new-place-button">Save</button>
      <button id="cancel-new-place-button">Cancel</button>
    <ul>
        <li>
        <label class="place-form-label" for="name"> Name </label><em class="place-form-note">Nirvana Deli</em>
        <div><input class="place-form-field" id="name" type="text"></div>
        </li>

        <li>
        <label class="place-form-label" for="address"> Address </label>
        <div><input class="place-form-field" id="address" type="text"></div>
        </li>

        <li>
        <label class="place-form-label" for="city"> City </label>
        <div><input class="place-form-field" id="city" name="city" type="text"></div>
        </li>

        <li>
        <label class="place-form-label" for="state"> State </label>
        <div><input class="place-form-field" id="state" name="state" type="text"></div>
        </li>

        <li>
        <label class="place-form-label" for="zipcode"> Zipcode </label>
        <div><input class="place-form-field" id="zipcode" name="zipcode" type="text"></div>
        </li>

        <li>
        <label class="place-form-label" for="phone"> Phone Number </label><em class="place-form-note">e.g. (555) 555-1212</em>
        <div><input class="place-form-field" id="phone" type="text"></div>
        </li>

        <li>
        <label class="place-form-label" for="url"> Website </label><em class="place-form-note">e.g. http://www.nirvanadeli.com</em>
        <div><input class="place-form-field" id="url" type="text"></div>
        </li>

      </ul>
  </div>

    <ul id="place-list"></ul>
    <div id="place-stats"></div>
</div>

    </div>
    <div class="yui3-u-2-3">
      <div id="preview">
<%--         <iframe style="width:100%;height:800px" src="${source.url}"></iframe> --%>
       </div>
    </div>
</div>



<!-- This template HTML will be used to render each place item. -->
<script type="text/x-template" id="place-item-template">
    <div class="place-view">
    <a href="{url}">{name}</a>, {address} {location}
    </div>

    <a href="#" class="place-remove" title="Remove this place">
        <span class="place-remove-icon"></span>
    </a>
</script>

<!-- This template HTML will be used to render the statistics at the bottom of the place list. -->
<script type="text/x-template" id="place-stats-template">
    <span class="place-count">
        <span class="place-count">{count}</span>
        <span class="place-count-label">{label}</span>.
    </span>

</script>

<script>

var Y = YUI({
  debug: true,
  useBrowserConsole: false,
  filter: 'debug',
    modules: {
        'model-sync-rest': {
                fullpath: "${pageContext.request.contextPath}/resources/js/model-sync-rest.js",
                requires: ['io-base', 'json-stringify']
        },
    }
}).use('io-base', 'event-focus', 'json', 'model', 'model-list', 'view', 'model-sync-rest', function (Y) {

var PlaceAppView, PlaceList, SourceModel, PlaceView;

// -- Model --------------------------------------------------------------------

// The PlaceModel class extends Y.Model and customizes it to use a localStorage
// sync provider (the source for that is further below) and to provide
// attributes and methods useful for place items.

PlaceModel = Y.PlaceModel = Y.Base.create('placeModel', Y.Model, [Y.ModelSync.REST], {
    root: 'places'
}, {
    ATTRS: {
      id: {},
      name: {value: ''},
      address: {value: ''},
      location: {value: ''},
      url: {value: ''},
      phone: {value: ''}
    }
});

// -- ModelList ----------------------------------------------------------------

// The PlaceList class extends Y.ModelList and customizes it to hold a list of
// PlaceModel instances, and to provide some convenience methods for getting
// information about the place items in the list.

PlaceList = Y.PlaceList = Y.Base.create('placeList', Y.ModelList, [Y.ModelSync.REST], {
    root: 'places',

    // This tells the list that it will hold instances of the PlaceModel class.
    model: PlaceModel,

});

// -- Place App View ------------------------------------------------------------

// The PlaceAppView class extends Y.View and customizes it to represent the
// main shell of the application, including the new item input fields and the
// list of place items.
//
// This class also takes care of initializing a PlaceList instance and creating
// and rendering a PlaceView instance for each place item when the list is
// initially loaded or reset.

PlaceAppView = Y.PlaceAppView = Y.Base.create('placeAppView', Y.View, [], {
    // The container node is the wrapper for this view.  All the view's events
    // will be delegated from the container. In this case, the #place-app
    // node already exists on the page, so we don't need to create it.
    container: Y.one('#place-app'),

    placeForm: Y.one('#place-form'),

    // The `template` property is a convenience property for holding a template
    // for this view. In this case, we'll use it to store the contents of the
    // #place-stats-template element, which will serve as the template for the
    // statistics displayed at the bottom of the list.
    template: Y.one('#place-stats-template').getContent(),

    // This is where we attach DOM events for the view. The `events` object is a
    // mapping of selectors to an object containing` one or more events to attach
    // to the node(s) matching each selector.
    events: {
      '#new-place-button': {click: 'createNewPlace'},
      '#save-new-place-button': {click: 'saveNewPlace'},
      '#cancel-new-place-button' : {click: 'cancelNewPlace'}
    },

    // The initializer runs when a PlaceAppView instance is created, and gives
    // us an opportunity to set up the view.
    initializer: function () {
        // Create a new PlaceList instance to hold the place items.
        var list = this.placeList = new PlaceList();

        // Update the display when a new item is added to the list, or when the
        // entire list is reset.
        list.after('add', this.add, this);
        list.after('reset', this.reset, this);

        // Re-render the stats in the footer whenever an item is added, removed
        // or changed, or when the entire list is reset.
        list.after(['add', 'reset', 'remove', 'placeModel:doneChange'], this.render, this);

        // Load saved items from localStorage, if available.
        list.load();
    },

    // The render function is called whenever a place item is added, removed, or
    // changed, thanks to the list event handler we attached in the initializer
    // above.
    render: function () {
        var placeList = this.placeList,
            stats = this.container.one('#place-stats');

        // If there are no place items, then clear the stats.
        if (placeList.isEmpty()) {
            stats.empty();
            return this;
        }

        // Update the statistics.
        stats.setContent(Y.Lang.sub(this.template, {
          count : placeList.size(),
          label : placeList.size() === 1 ? 'place' : 'places'
        }));

        return this;
    },

    // -- Event Handlers -------------------------------------------------------

    // Creates a new PlaceView instance and renders it into the list whenever a place item is added to the list.
    add: function (e) {
        var view = new PlaceView({model: e.model});
        this.container.one('#place-list').append(view.render().container);
    },

    createNewPlace: function (e) {
        this.container.one('#place-form').removeClass('invisible');
        this.container.one('#new-place').addClass('invisible')
    },

    saveNewPlace: function(e) {
       this.container.one('#place-form').addClass('invisible');
       this.container.one('#new-place').removeClass('invisible')

       var place = {
            name: Y.Lang.trim(this.placeForm.one('#name').get('value')),
            address: Y.Lang.trim(this.placeForm.one('#address').get('value')),
            city: Y.Lang.trim(this.placeForm.one('#city').get('value')),
            state: Y.Lang.trim(this.placeForm.one('#state').get('value')),
            zipcode: Y.Lang.trim(this.placeForm.one('#zipcode').get('value')),
            phone: Y.Lang.trim(this.placeForm.one('#phone').get('value')),
            url: Y.Lang.trim(this.placeForm.one('#url').get('value'))
       }

      this.placeList.create(place);
    },

    cancelNewPlace: function(e) {
      Y.one('#place-form').addClass('invisible');
      Y.one('#new-place').removeClass('invisible')
    },

    // Turns off the hover state on a place item.
    hoverOff: function (e) {
        e.currentTarget.removeClass('place-hover');
    },

    // Turns on the hover state on a place item.
    hoverOn: function (e) {
        e.currentTarget.addClass('place-hover');
    },

    // Creates and renders views for every place item in the list when the entire list is reset.
    reset: function (e) {
        var fragment = Y.one(Y.config.doc.createDocumentFragment());

        Y.Array.each(e.models, function (model) {
            var view = new PlaceView({model: model});
            fragment.append(view.render().container);
        });

        this.container.one('#place-list').setContent(fragment);
    }
});



// -- Place item view -----------------------------------------------------------

// The PlaceView class extends Y.View and customizes it to represent the content
// of a single place item in the list. It also handles DOM events on the item to
// allow it to be edited and removed from the list.

PlaceView = Y.PlaceView = Y.Base.create('placeView', Y.View, [], {
    // Specifying an HTML string as this view's container element causes that
    // HTML to be automatically converted into an unattached Y.Node instance.
    // The PlaceAppView (above) will take care of appending it to the list.
    container: '<li class="place-item"/>',

    // The template property holds the contents of the #place-item-template
    // element, which will be used as the HTML template for each place item.
    template: Y.one('#place-item-template').getContent(),

    // Delegated DOM events to handle this view's interactions.
    events: {
        // When the text of this place item is clicked or focused, switch to edit
        // mode to allow editing.
        '.place-content': {
            click: 'edit',
            focus: 'edit'
        },

        // On the edit field, when enter is pressed or the field loses focus,
        // save the current value and switch out of edit mode.
        '.place-input'   : {
            blur    : 'save',
            keypress: 'enter'
        },

        // When the remove icon is clicked, delete this place item.
        '.place-remove': {click: 'remove'}
    },

    initializer: function () {
        // The model property is set to a PlaceModel instance by PlaceAppView when
        // it instantiates this PlaceView.
        var model = this.model;

        // Re-render this view when the model changes, and destroy this view
        // when the model is destroyed.
        model.after('change', this.render, this);
        model.after('destroy', this.destroy, this);
    },

    render: function () {
        var container = this.container,
            model     = this.model;

        var location = model.getAsHTML('address');

        if (model.city) {
          location = location + ', ' + model.getAsHTML('city');
        }
        if (model.state) {
          location = location + ', ' + model.getAsHTML('state');
        }
        if (model.zipcode) {
          location = location + ', ' + model.getAsHTML('zipcode');
        }
        if (model.country) {
          location = location + ', ' + model.getAsHTML('country');
        }


        container.setContent(Y.Lang.sub(this.template, {
          name     : model.getAsHTML('name'),
          address  : model.getAsHTML('address'),
          location : model.getAsHTML('location'),
          url      : model.getAsHTML('url'),
          phone    : model.getAsHTML('phone'),
          id       : model.getAsHTML('id')
        }));

        return this;
    },

    // -- Event Handlers -------------------------------------------------------

    // Toggles this item into edit mode.
    edit: function () {
        this.container.addClass('editing');
        this.inputNode.focus();
    },

    // Removes this item from the list.
    remove: function (e) {
        e.preventDefault();
        this.constructor.superclass.remove.call(this);
        this.model.destroy({'delete': true});
    },

    // Toggles this item out of edit mode and saves it.
    save: function () {
        this.container.removeClass('editing');
        this.model.set('name', this.inputNode.get('value')).save();
        this.model.set('address', this.inputNode.get('value')).save();
    },

});

// -- Start your engines! ------------------------------------------------------

// Finally, all we have to do is instantiate a new PlaceAppView to set everything
// in motion and bring our place list into existence.
new PlaceAppView();

});
</script>

</body>
</html>
