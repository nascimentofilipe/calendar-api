import { Component, OnInit } from '@angular/core';
import { Contact } from './contact';
import { ContactService } from '../contact.service';

import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { ContactDetailsComponent } from '../contact-details/contact-details.component';
import { PageEvent } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {

  form: FormGroup;
  contacts: Contact[] = [];
  columns = ['picture', 'id', 'name', 'email', 'favorite'];
  totalElements = 0;
  page = 0;
  size = 10;
  pageSizeOptions: number[] = [10];


  constructor(
    private service: ContactService,
    private fb: FormBuilder,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.buildForm();
    this.listContacts(this.page, this.size);
  }

  buildForm() {
    this.form = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]]
    })
  }

  listContacts(page = 0, size = 5) {
    this.service.list(page, size).subscribe(response => {
      this.contacts = response.content;
      this.totalElements = response.totalElements;
      this.page = response.number;
    })
  }

  toFavorite(contact: Contact) {
    this.service.toFavorite(contact).subscribe(response => {
      contact.favorite = !contact.favorite;
    })
  }

  submit() {
    const formValues = this.form.value;
    const contact: Contact = new Contact(formValues.name, formValues.email);
    this.service.save(contact).subscribe(response => {
      let list: Contact[] = [... this.contacts, response]
      this.contacts = list;
      this.snackBar.open('Contact successfully added!',
        'Success!', {
        duration: 2000
      })
      this.form.reset();
    });
  }

  uploadPicture(event, contact) {
    const files = event.target.files;
    if (files) {
      const pic = files[0];
      const formData: FormData = new FormData;
      formData.append("picture", pic);

      this.service
        .upload(contact, formData)
        .subscribe(response => this.listContacts());
    }
  }

  visualisePicture(contact: Contact) {
    this.dialog.open(ContactDetailsComponent, {
      width: '400px',
      height: '450px',
      data: contact
    })
  }

  paginator(event: PageEvent) {
    this.page = event.pageIndex;
    this.listContacts(this.page, this.size);
  }
}
